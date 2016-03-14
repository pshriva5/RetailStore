package com.thoughtw.retail.sample.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.mikepenz.actionitembadge.library.ActionItemBadge;
import com.mikepenz.actionitembadge.library.utils.BadgeStyle;
import com.thoughtw.retail.animation.GuillotineAnimation;
import com.thoughtw.retail.sample.App;
import com.thoughtw.retail.sample.R;
import com.thoughtw.retail.sample.adapter.ProductsListingAdapter;
import com.thoughtw.retail.sample.db.ProductData;
import com.thoughtw.retail.sample.di.components.DbComponent;
import com.thoughtw.retail.sample.model.CartItem;
import com.thoughtw.retail.sample.model.CartUpdateListener;
import com.thoughtw.retail.sample.model.ProductModel;
import com.thoughtw.retail.sample.utils.RecyclerViewClickListener;
import com.thoughtw.retail.sample.widget.FontTextView;
import com.thoughtw.retail.sample.widget.SpacesItemDecoration;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends BaseActivity implements View.OnClickListener, CartUpdateListener, RecyclerViewClickListener {
  private static final long RIPPLE_DURATION = 250;

  @InjectView(R.id.toolbar)
  Toolbar toolbar;
  @InjectView(R.id.root)
  FrameLayout root;
  @InjectView(R.id.content_hamburger)
  View contentHamburger;
  @InjectView(R.id.tittle_fragment)
  FontTextView mTittleFragment;
  @InjectView(R.id.list_grid)
  RecyclerView mProductsRecyclerView;

  @Inject
  ProductData productData;
  private ArrayList<ProductModel> list = new ArrayList<>();
  private ProductsListingAdapter adapter;
  private ImageView homeIcon;
  private FontTextView homeTittle;
  private ImageView electronicsIcon;
  private FontTextView electronicsTittle;
  private ImageView furnitureIcon;
  private FontTextView furnitureTittle;
  private GuillotineAnimation guillotineAnimation;
  private Menu menu;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.inject(this);
    setUpToolBar();
    setUpCart();
    injectDependency();
    View guillotineMenu = LayoutInflater.from(this).inflate(R.layout.drawer_items_menu, null);
    setUpOnClicks(guillotineMenu);
    setUpGuillotineAnimation(guillotineMenu);
    root.addView(guillotineMenu);
    ButterKnife.inject(guillotineMenu);
    loadHomeScreenListings();
  }

  private void setUpCart() {
    this.mApp.setCartUpdateListener(this);
    this.mApp.populateCart();

  }

  private void injectDependency() {
    DbComponent
        component = ((App) getApplication()).getDbComponent();
    component.inject(this);

  }

  private void loadHomeScreenListings() {
    mTittleFragment.setText("HOME");
    mProductsRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
    adapter = new ProductsListingAdapter(this, list, MainActivity.this);
    mProductsRecyclerView.setAdapter(adapter);
    SpacesItemDecoration decoration = new SpacesItemDecoration(16);
    mProductsRecyclerView.addItemDecoration(decoration);
    filterListings(productData.getAllProductList());
  }

  @Override
  public void recyclerViewListClicked(View v, int position) {
    this.navigator.navigateToProductDetails(this, list.get(position));
  }

  private void setUpToolBar() {
    if (toolbar != null) {
      setSupportActionBar(toolbar);
      getSupportActionBar().setTitle(null);
    }
  }

  private void setUpGuillotineAnimation(View guillotineMenu) {
    initItemOfGuillotine(guillotineMenu);
    guillotineAnimation = new GuillotineAnimation.GuillotineBuilder(guillotineMenu, guillotineMenu.findViewById(R.id.guillotine_hamburger), contentHamburger)
        .setStartDelay(RIPPLE_DURATION)
        .setActionBarViewForAnimation(toolbar)
        .setClosedOnStart(true)
        .build();
  }

  private void initItemOfGuillotine(View view) {
    electronicsIcon = (ImageView) view.findViewById(R.id.elec_icon);
    furnitureIcon = (ImageView) view.findViewById(R.id.furniture_icon);
    homeIcon = (ImageView) view.findViewById(R.id.home_icon);
    electronicsTittle = (FontTextView) view.findViewById(R.id.elec_tittle);
    furnitureTittle = (FontTextView) view.findViewById(R.id.furniture_tittle);
    homeTittle = (FontTextView) view.findViewById(R.id.home_tittle);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.cart_badge, menu);
    this.menu = menu;
    updateCartBadge();

    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle item selection
    switch (item.getItemId()) {
      case R.id.item_samplebadge:
        this.navigator.navigateToViewCartDetails(this);
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  private void updateCartBadge() {
    if (menu != null) {
      ActionItemBadge.update(this, menu.findItem(R.id.item_samplebadge), getResources().getDrawable(R.drawable.ic_action_shopping108), new BadgeStyle(BadgeStyle.Style.LARGE, R.layout.menu_badge, Color.RED, Color.GRAY, Color.parseColor("#ffffff")), mApp.getCart().size());
    }
  }

  private void setUpOnClicks(View guillotineMenu) {
    guillotineMenu.findViewById(R.id.activity_group).setOnClickListener(this);
    guillotineMenu.findViewById(R.id.electronic_group).setOnClickListener(this);
    guillotineMenu.findViewById(R.id.furniture_group).setOnClickListener(this);
  }

  @Override
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.activity_group:
        updateItemSelection(homeIcon, homeTittle, R.drawable.ic_action_house_active, getResources().getString(R.string.activity), productData.getAllProductList());
        break;
      case R.id.electronic_group:
        updateItemSelection(electronicsIcon, electronicsTittle, R.drawable.ic_action_plug_active, getResources().getString(R.string.electronics), productData.getElectronicProductList());
        break;
      case R.id.furniture_group:
        updateItemSelection(furnitureIcon, furnitureTittle, R.drawable.ic_action_furniture_active, getResources().getString(R.string.furniture), productData.getFurnitureProductList());
        break;
    }
  }

  private void updateItemSelection(ImageView activeImage, FontTextView activeTittle, int icon, String toolBarTittle, ArrayList<ProductModel> list) {
    resetViewsItems();
    activeImage.setImageDrawable(ResourcesCompat.getDrawable(getResources(), icon, null));
    activeTittle.setTextColor(Color.parseColor("#30d1d5"));
    mTittleFragment.setText(toolBarTittle);
    guillotineAnimation.close();
    filterListings(list);
  }

  private void resetViewsItems() {
    homeIcon.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_action_home, null));
    electronicsIcon.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_action_plug, null));
    furnitureIcon.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_action_furniture, null));
    homeTittle.setTextColor(Color.parseColor("#ffffff"));
    electronicsTittle.setTextColor(Color.parseColor("#ffffff"));
    furnitureTittle.setTextColor(Color.parseColor("#ffffff"));
  }


  public void updateCart(ArrayList<CartItem> arrayList) {
    updateCartBadge();
  }

  public void filterListings(ArrayList<ProductModel> list) {
    this.list.clear();
    this.list.addAll(list);
    adapter.notifyDataSetChanged();
  }

  protected void onResume() {
    super.onResume();
    updateCart(this.mApp.getCart());
  }
}
