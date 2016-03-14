package com.thoughtw.retail.sample.adapter;

import android.content.Context;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.thoughtw.retail.sample.R;
import com.thoughtw.retail.sample.model.CartItem;
import com.thoughtw.retail.sample.utils.RecyclerViewClickListener;

import java.util.ArrayList;


public class ViewCartAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  ArrayList<CartItem> contents;


  static final int TYPE_HEADER = 0;
  static final int TYPE_CELL = 1;
  private Context context;
  private RecyclerViewClickListener listner;

  public ViewCartAdapter(ArrayList<CartItem> contents, Context context, RecyclerViewClickListener itemListener) {
    this.contents = contents;
    this.context = context;
    this.listner = itemListener;
  }

  @Override
  public int getItemViewType(int position) {
    if (position == contents.size() - 1) {
      return TYPE_HEADER;
    } else {
      return TYPE_CELL;
    }
  }

  @Override
  public int getItemCount() {
    return contents.size();
  }

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = null;

    switch (viewType) {
      case TYPE_HEADER: {
        view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_cart_total_header, parent, false);
        TotalViewHolder holder = new TotalViewHolder(view);
        return holder;
      }
      case TYPE_CELL: {
        view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_cart_product, parent, false);
        ItemViewHolder itemViewHolder = new ItemViewHolder(view);
        return itemViewHolder;
      }
    }
    return null;
  }


  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
    CartItem cartItem = contents.get(i);
    switch (viewHolder.getItemViewType()) {
      case TYPE_HEADER:
        TotalViewHolder holder = (TotalViewHolder) viewHolder;
        float tax = (float) (cartItem.getSubTotal() * 10.0 / 100.0);
        holder.tvSubTotal.setText("Rs. " + cartItem.getSubTotal());
        holder.tvTax.setText("Rs. " + tax);
        tax = tax + cartItem.getSubTotal();
        holder.tvFinalAmount.setText("Rs. " + tax);
        break;
      case TYPE_CELL:
        ItemViewHolder itemViewHolder = (ItemViewHolder) viewHolder;
        itemViewHolder.tvItemPrice.setText("Rs. " + cartItem.getPrice());
        itemViewHolder.tvItemName.setText(cartItem.getTitle());
        itemViewHolder.tvQuantity.setText("Quantity : " + cartItem.getQuantity() + "*" + cartItem.getRegularPrice());
        itemViewHolder.thumbnail.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(), cartItem.getImagePrimary(), null));
        break;
    }
  }

  public class TotalViewHolder extends RecyclerView.ViewHolder {
    private TextView tvSubTotal, tvTax, tvFinalAmount;

    public TotalViewHolder(View view) {
      super(view);
      tvFinalAmount = (TextView) view.findViewById(R.id.final_amount);
      tvTax = (TextView) view.findViewById(R.id.tax_amount);
      tvSubTotal = (TextView) view.findViewById(R.id.sub_total);
    }
  }

  public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView tvItemName;
    private TextView tvItemPrice;
    private TextView tvQuantity;
    ImageView thumbnail;
    private ImageView options;
    RelativeLayout mRoot;

    public ItemViewHolder(View view) {
      super(view);
      view.setOnClickListener(this);
      tvItemPrice = (TextView) view.findViewById(R.id.price);
      tvItemName = (TextView) view.findViewById(R.id.title);
      thumbnail = (ImageView) view.findViewById(R.id.product_thumbnail);
      tvQuantity = (TextView) view.findViewById(R.id.quantity);
      mRoot = (RelativeLayout) view.findViewById(R.id.root);
      options = (ImageView) view.findViewById(R.id.options);
      options.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
      listner.recyclerViewListClicked(v, this.getLayoutPosition());
    }
  }

}