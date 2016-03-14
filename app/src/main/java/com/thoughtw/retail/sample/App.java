package com.thoughtw.retail.sample;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;
import android.support.multidex.MultiDex;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.github.johnpersano.supertoasts.SuperToast;
import com.thoughtw.retail.sample.db.Preference;
import com.thoughtw.retail.sample.db.ProductData;
import com.thoughtw.retail.sample.di.components.AppComponent;
import com.thoughtw.retail.sample.di.components.DaggerAppComponent;
import com.thoughtw.retail.sample.di.components.DaggerDbComponent;
import com.thoughtw.retail.sample.di.components.DaggerPreferenceComponent;
import com.thoughtw.retail.sample.di.components.DbComponent;
import com.thoughtw.retail.sample.di.components.PreferenceComponent;
import com.thoughtw.retail.sample.di.modules.AppModule;
import com.thoughtw.retail.sample.di.modules.DbModule;
import com.thoughtw.retail.sample.di.modules.PreferenceModule;
import com.thoughtw.retail.sample.model.CartItem;
import com.thoughtw.retail.sample.model.CartUpdateListener;

import java.util.ArrayList;
import java.util.Iterator;

public class App extends Application implements CartUpdateListener {
  private static final String CANARO_EXTRA_BOLD_PATH = "fonts/canaro_extra_bold.otf";
  public static Typeface canaroExtraBold;
  private ArrayList<CartItem> mCartItems;
  private CartUpdateListener mCartUpdateListener;
  private AppComponent appComponent;
  private DbComponent dbComponent;
  private PreferenceComponent preferenceComponent;


  @Override
  public void onCreate() {
    super.onCreate();
    MultiDex.install(this);
    initTypeface();
    Fresco.initialize(getApplicationContext());
    this.mCartItems = new ArrayList();
    initializeDagger();
  }

  private void initializeDagger() {
    dbComponent = DaggerDbComponent.builder().dbModule(new DbModule(new ProductData(this))).build();
    dbComponent.inject(this);
    appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
    appComponent.inject(this);
    preferenceComponent = DaggerPreferenceComponent.builder().preferenceModule(new PreferenceModule(new Preference(getSharedPreferences(Preference.RETAIL_SHARED_PREFERENCES_FILE, Context.MODE_PRIVATE)))).build();
    preferenceComponent.inject(this);

  }

  public App() {
    this.mCartItems = new ArrayList();
  }

  private void initTypeface() {
    canaroExtraBold = Typeface.createFromAsset(getAssets(), CANARO_EXTRA_BOLD_PATH);
  }

  public AppComponent getApplicationComponent() {
    return this.appComponent;
  }

  public PreferenceComponent getPreferenceComponent() {
    return this.preferenceComponent;
  }

  public DbComponent getDbComponent() {
    return this.dbComponent;
  }

  public void populateCart() {
    this.mCartItems = new ArrayList();
    if (preferenceComponent.providePreferences().getCartItems(this) != null) {
      mCartItems.addAll(preferenceComponent.providePreferences().getCartItems(this));
      setCart(this.mCartItems);
    }
  }

  public long getCartTotal(ArrayList<CartItem> cartItems) {
    long totalPrice = 0;
    Iterator i$ = cartItems.iterator();
    while (i$.hasNext()) {
      totalPrice += ((CartItem) i$.next()).getPrice();
    }
    return totalPrice;
  }

  public void setCartUpdateListener(CartUpdateListener cartUpdateListener) {
    this.mCartUpdateListener = cartUpdateListener;
  }

  public CartUpdateListener getCartUpdateListener() {
    return this.mCartUpdateListener;
  }

  public void setCart(ArrayList<CartItem> cartItems) {
    this.mCartItems = cartItems;
    this.mCartUpdateListener.updateCart(cartItems);
  }

  public void showToast(Context context, String message) {
    SuperToast superToast = new SuperToast(context);
    SuperToast.cancelAllSuperToasts();
    superToast.setAnimations(SuperToast.Animations.POPUP);
    superToast.setDuration(SuperToast.Duration.SHORT);
    superToast.setText(" " + message);
    superToast.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
    superToast.setBackground(R.drawable.border_toast);
    superToast.show();
  }

  public ArrayList<CartItem> getCart() {
    return this.mCartItems;
  }

  @Override
  public void updateCart(ArrayList<CartItem> cartItems) {
    setCart(cartItems);
  }
}
