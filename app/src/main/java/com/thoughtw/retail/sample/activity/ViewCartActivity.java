package com.thoughtw.retail.sample.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.thoughtw.retail.sample.R;
import com.thoughtw.retail.sample.adapter.ViewCartAdapter;
import com.thoughtw.retail.sample.db.Preference;
import com.thoughtw.retail.sample.di.PerActivity;
import com.thoughtw.retail.sample.model.CartItem;
import com.thoughtw.retail.sample.model.ProductModel;
import com.thoughtw.retail.sample.utils.RecyclerViewClickListener;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

@PerActivity
public class ViewCartActivity extends BaseActivity implements RecyclerViewClickListener {
  @InjectView(R.id.product_list)
  RecyclerView mProductList;
  @InjectView(R.id.toolbar)
  Toolbar toolbar;
  @InjectView(R.id.empty_view)
  LinearLayout mEmptyView;
  private Preference preference;
  ViewCartAdapter mCartAdapter;
  private ArrayList<CartItem> mContentItems = new ArrayList<>();

  public static Intent getCallingIntent(Context context) {
    return new Intent(context, ViewCartActivity.class);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_view_cart);
    ButterKnife.inject(this);
    setUpToolBar();
    injectPreference();
    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ViewCartActivity.this);
    mProductList.setLayoutManager(layoutManager);
    mProductList.setHasFixedSize(true);
    mCartAdapter = new ViewCartAdapter(mContentItems, this, this);
    mProductList.setAdapter(mCartAdapter);
    this.mApp.populateCart();
    if (preference.getCartItems(this) != null) {
      prepareCart();
    } else {
      showEmptyView();
    }
  }

  private void prepareCart() {
    mContentItems.clear();
    mContentItems.addAll(preference.getCartItems(this));
    mContentItems.add(new CartItem("", "", 0, 0L, "", 0, mApp.getCartTotal(mApp.getCart()), 0L));
    mCartAdapter.notifyDataSetChanged();
  }

  private void showEmptyView() {
    mProductList.setVisibility(View.INVISIBLE);
    mEmptyView.setVisibility(View.VISIBLE);
  }

  private void injectPreference() {
    preference = mApp.getPreferenceComponent().providePreferences();
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        onBackPressed();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
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
  public void recyclerViewListClicked(View v, int position) {
    switch (v.getId()) {
      case R.id.options:
        showPopUp(v, position);
        break;
      default:
        navigateScreen(position);
        break;
    }
  }

  public void showPopUp(View view, final int position) {
    PopupMenu popup = new PopupMenu(this, view);
    popup.getMenuInflater().inflate(R.menu.remove_item_from_cart, popup.getMenu());
    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
      public boolean onMenuItemClick(MenuItem item) {
        refreshRecyclerView(position);
        return true;
      }
    });
    popup.show();
  }

  private void refreshRecyclerView(int position) {
    if (mContentItems.size() == 2) {
      mContentItems.clear();
      mCartAdapter.notifyDataSetChanged();
      preference.setCartItems(ViewCartActivity.this, null);
      showEmptyView();
    } else {
      mContentItems.remove(position);
      mContentItems.remove(mContentItems.size() - 1);
      preference.setCartItems(ViewCartActivity.this, mContentItems);
      mApp.populateCart();
      mContentItems.add(new CartItem("", "", 0, 0L, "", 0, mApp.getCartTotal(mApp.getCart()), 0L));
      mCartAdapter.notifyDataSetChanged();
    }
    mApp.populateCart();
  }

  private void navigateScreen(int position) {
    CartItem cartItem = mContentItems.get(position);
    ProductModel productModel = new ProductModel(cartItem.getDescription(), cartItem.getItemId(), cartItem.getImagePrimary(), cartItem.getRegularPrice(), cartItem.getTitle());
    this.navigator.navigateToProductDetails(this, productModel);
  }
}
