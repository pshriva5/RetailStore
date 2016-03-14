package com.thoughtw.retail.sample.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.mikepenz.actionitembadge.library.ActionItemBadge;
import com.mikepenz.actionitembadge.library.utils.BadgeStyle;
import com.thoughtw.retail.sample.R;
import com.thoughtw.retail.sample.adapter.ViewProductDetailAdapter;
import com.thoughtw.retail.sample.db.Preference;
import com.thoughtw.retail.sample.model.CartItem;
import com.thoughtw.retail.sample.model.CartUpdateListener;
import com.thoughtw.retail.sample.model.ProductModel;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class ViewProductDetails extends BaseActivity implements CartUpdateListener {
  private static final String INTENT_EXTRA_PARAM_USER_ID = "PRODUCT_OBJECT";

  public static Intent getCallingIntent(Context context, ProductModel item) {
    Intent callingIntent = new Intent(context, ViewProductDetails.class);
    callingIntent.putExtra(INTENT_EXTRA_PARAM_USER_ID, item);
    return callingIntent;
  }


  @InjectView(R.id.product_list)
  RecyclerView mProductList;
  @InjectView(R.id.toolbar)
  Toolbar toolbar;
  private Menu menu;
  ViewProductDetailAdapter mItemAdapter;
  private ArrayList<ProductModel> mContentItems = new ArrayList<>();
  private Preference preference;
  private ProductModel productModel;
  private ArrayList<CartItem> cartItems = new ArrayList<>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_view_product_detail);
    ButterKnife.inject(this);
    setUpToolBar();
    setUpCart();
    initializePreference();
    productModel = (ProductModel) getIntent().getSerializableExtra(INTENT_EXTRA_PARAM_USER_ID);
    initializeCards();
    mContentItems.add(productModel);
    mContentItems.add(productModel);
    mItemAdapter.notifyDataSetChanged();
  }

  private void initializePreference() {
    this.preference = mApp.getPreferenceComponent().providePreferences();
  }

  private void initializeCards() {
    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ViewProductDetails.this);
    mProductList.setLayoutManager(layoutManager);
    mProductList.setHasFixedSize(true);
    mItemAdapter = new ViewProductDetailAdapter(mContentItems, this);
    mProductList.setAdapter(mItemAdapter);
  }

  private void setUpCart() {
    this.mApp.setCartUpdateListener(this);
    this.mApp.populateCart();
  }


  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.cart_badge, menu);
    this.menu = menu;
    updateCartBadge();
    return super.onCreateOptionsMenu(menu);
  }

  private void updateCartBadge() {
    if (menu != null) {
      ActionItemBadge.update(this, menu.findItem(R.id.item_samplebadge), getResources().getDrawable(R.drawable.ic_action_shopping108), new BadgeStyle(BadgeStyle.Style.LARGE, R.layout.menu_badge, Color.RED, Color.GRAY, Color.parseColor("#ffffff")), mApp.getCart().size());
    }
  }

  @OnClick(R.id.add_to_bag)
  public void addItemInPreference(View view) {
    if (preference.getCartItems(this) != null) {
      if (!checkItemExistInCart(preference.getCartItems(this))) {
        mApp.showToast(this, productModel.getName() + " Added in Bag");
        saveInPreference(false);
      }
    } else {
      saveInPreference(true);
      mApp.showToast(this, productModel.getName() + " Added in Bag");
    }
  }

  private void saveInPreference(Boolean firstValue) {
    cartItems.clear();
    if (!firstValue) {
      cartItems.addAll(preference.getCartItems(this));
    }
    cartItems.add(prepareItem());
    preference.setCartItems(this, cartItems);
    setUpCart();
  }

  private CartItem prepareItem() {
    CartItem cartItem = new CartItem(productModel.getDescription(), productModel.getEntityId(), productModel.getPrimaryImage(), productModel.getFinalPrice(), productModel.getName(), 1, 0L, productModel.getFinalPrice());
    return cartItem;
  }

  private Boolean checkItemExistInCart(ArrayList<CartItem> cartItems) {
    for (CartItem item : cartItems) {
      if (item.getItemId().equals(productModel.getEntityId())) {
        item.setQuantity(item.getQuantity() + 1);
        item.setPrice(productModel.getFinalPrice() * item.getQuantity());
        preference.setCartItems(this, cartItems);
        mApp.showToast(this, "Quantity Updated in Bag");
        return true;
      }
    }
    return false;
  }

  private void setUpToolBar() {
    if (toolbar != null) {
      setSupportActionBar(toolbar);
      getSupportActionBar().setTitle(null);
      getSupportActionBar().setHomeButtonEnabled(true);
      getSupportActionBar().setDisplayShowHomeEnabled(true);
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle item selection
    switch (item.getItemId()) {
      case android.R.id.home:
        onBackPressed();
        return true;
      case R.id.item_samplebadge:
        this.navigator.navigateToViewCartDetails(this);
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  public void updateCart(ArrayList<CartItem> arrayList) {
    updateCartBadge();
  }

  protected void onResume() {
    super.onResume();
    updateCart(this.mApp.getCart());
  }
}
