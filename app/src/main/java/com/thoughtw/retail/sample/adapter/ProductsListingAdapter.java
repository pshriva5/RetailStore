package com.thoughtw.retail.sample.adapter;

import android.content.Context;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.view.SimpleDraweeView;
import com.thoughtw.retail.sample.R;
import com.thoughtw.retail.sample.model.ProductModel;
import com.thoughtw.retail.sample.utils.RecyclerViewClickListener;

import java.util.ArrayList;

public class ProductsListingAdapter extends RecyclerView.Adapter<ProductsListingAdapter.ProductItemView> {

  private Context context;
  private ArrayList<ProductModel> productModelArrayList;
  private static RecyclerViewClickListener itemListener;

  public ProductsListingAdapter(Context context, ArrayList<ProductModel> productModelArrayList, RecyclerViewClickListener itemListener) {
    this.context = context;
    this.productModelArrayList = productModelArrayList;
    this.itemListener = itemListener;
  }

  @Override
  public ProductItemView onCreateViewHolder(ViewGroup parent, int viewType) {
    View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item, parent, false);
    ProductItemView productItemView = new ProductItemView(layoutView);
    return productItemView;
  }

  @Override
  public void onBindViewHolder(ProductItemView holder, int position) {
    ProductModel model = productModelArrayList.get(position);
    GenericDraweeHierarchy hierarchy = holder.mProductImageView.getHierarchy();
    holder.nameTextView.setText(model.getName());
    holder.priceTextView.setText("Rs. " + model.getFinalPrice());
    hierarchy.setPlaceholderImage(ResourcesCompat.getDrawable(context.getResources(), model.getPrimaryImage(), null));
  }


  @Override
  public int getItemCount() {
    return productModelArrayList.size();
  }

  class ProductItemView extends RecyclerView.ViewHolder implements View.OnClickListener {
    SimpleDraweeView mProductImageView;
    TextView nameTextView, priceTextView;

    public ProductItemView(View itemView) {
      super(itemView);
      itemView.setOnClickListener(this);
      mProductImageView = (SimpleDraweeView) itemView.findViewById(R.id.product_thumbnail);
      nameTextView = (TextView) itemView.findViewById(R.id.img_name);
      priceTextView = (TextView) itemView.findViewById(R.id.price);
    }

    @Override
    public void onClick(View v) {
      itemListener.recyclerViewListClicked(v, this.getLayoutPosition());
    }
  }
}