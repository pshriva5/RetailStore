package com.thoughtw.retail.sample.adapter;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.thoughtw.retail.sample.R;
import com.thoughtw.retail.sample.model.ProductModel;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;


public class ViewProductDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  ArrayList<ProductModel> itemDetail;
  static final int TYPE_HEADER = 0;
  static final int TYPE_CELL = 1;
  private ViewPager viewpager;
  private CircleIndicator indicator;
  private TextView tvItemName;
  private TextView tvItemPrice;
  private TextView tvItemDescription;
  private Context mContext;

  public ViewProductDetailAdapter(ArrayList<ProductModel> itemDetail, Context context) {
    this.itemDetail = itemDetail;
    this.mContext = context;
  }

  @Override
  public int getItemViewType(int position) {
    switch (position) {
      case 0:
        return TYPE_HEADER;
      default:
        return TYPE_CELL;
    }
  }

  @Override
  public int getItemCount() {
    return itemDetail.size();
  }

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = null;

    switch (viewType) {
      case TYPE_HEADER: {
        view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_images_product, parent, false);
        viewpager = (ViewPager) view.findViewById(R.id.image_viewpager);
        tvItemPrice = (TextView) view.findViewById(R.id.item_price);
        tvItemName = (TextView) view.findViewById(R.id.item_name);
        indicator = (CircleIndicator) view.findViewById(R.id.indicator);
        return new RecyclerView.ViewHolder(view) {
        };
      }
      case TYPE_CELL: {
        view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_detail_view_product, parent, false);
        tvItemDescription = (TextView) view.findViewById(R.id.description);
        return new RecyclerView.ViewHolder(view) {
        };
      }
    }
    return null;
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    ProductModel productModel = itemDetail.get(position);
    switch (getItemViewType(position)) {
      case TYPE_HEADER:
        viewpager.setAdapter(new ImagePagerAdapter(mContext, productModel.getEntityId()));
        indicator.setViewPager(viewpager);
        tvItemPrice.setText("Rs. " + productModel.getFinalPrice());
        tvItemName.setText(productModel.getName());
        break;
      case TYPE_CELL:
        tvItemDescription.setText(productModel.getDescription());
        break;
    }
  }


}