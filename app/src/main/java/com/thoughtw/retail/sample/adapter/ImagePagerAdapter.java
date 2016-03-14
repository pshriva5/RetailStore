package com.thoughtw.retail.sample.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.thoughtw.retail.sample.R;

import java.util.ArrayList;
import java.util.Arrays;

class ImagePagerAdapter extends PagerAdapter {

  Context mContext;
  LayoutInflater mLayoutInflater;
  ArrayList<Integer> mResources;

  public ImagePagerAdapter(Context context, String itemId) {
    mContext = context;
    mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    initializeImageArray(itemId);
  }

  @Override
  public int getCount() {
    return mResources.size();
  }

  @Override
  public boolean isViewFromObject(View view, Object object) {
    return view == ((LinearLayout) object);
  }

  @Override
  public Object instantiateItem(ViewGroup container, int position) {
    View itemView = mLayoutInflater.inflate(R.layout.item_pager, container, false);
    ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
    imageView.setImageResource(mResources.get(position));
    container.addView(itemView);
    return itemView;
  }

  private void initializeImageArray(String itemId) {
    switch (itemId) {
      case "0Microwave Oven":
        mResources = new ArrayList<>(Arrays.asList(R.drawable.m1_big, R.drawable.special_offer, R.drawable.new_coll));
        break;
      case "1Microwave Oven":
        mResources = new ArrayList<>(Arrays.asList(R.drawable.m2_big, R.drawable.m2_big_2, R.drawable.m2_big_3));
        break;
      case "2Microwave Oven":
        mResources = new ArrayList<>(Arrays.asList(R.drawable.m3_big, R.drawable.special_offer, R.drawable.new_coll));
        break;
      case "0Television":
        mResources = new ArrayList<>(Arrays.asList(R.drawable.t1_big, R.drawable.special_offer, R.drawable.new_coll));
        break;
      case "1Television":
        mResources = new ArrayList<>(Arrays.asList(R.drawable.t2_big_1, R.drawable.t2_big_2));
        break;
      case "2Television":
        mResources = new ArrayList<>(Arrays.asList(R.drawable.t3_big, R.drawable.special_offer, R.drawable.new_coll));
        break;
      case "0Vacuum Cleaner":
        mResources = new ArrayList<>(Arrays.asList(R.drawable.v1_big, R.drawable.special_offer, R.drawable.new_coll));
        break;
      case "1Vacuum Cleaner":
        mResources = new ArrayList<>(Arrays.asList(R.drawable.v2_big, R.drawable.special_offer, R.drawable.new_coll));
        break;
      case "2Vacuum Cleaner":
        mResources = new ArrayList<>(Arrays.asList(R.drawable.v3_big, R.drawable.special_offer, R.drawable.new_coll));
        break;
      case "0Table":
        mResources = new ArrayList<>(Arrays.asList(R.drawable.table1_big_1, R.drawable.table1_big_2));
        break;
      case "1Table":
        mResources = new ArrayList<>(Arrays.asList(R.drawable.table2_big_1, R.drawable.table2_big_2));
        break;
      case "2Table":
        mResources = new ArrayList<>(Arrays.asList(R.drawable.table3_big_1, R.drawable.table3_big_2, R.drawable.table3_big_3));
        break;
      case "0Chair":
        mResources = new ArrayList<>(Arrays.asList(R.drawable.chair1_big, R.drawable.chair1_big_2, R.drawable.chair1_big_3));
        break;
      case "1Chair":
        mResources = new ArrayList<>(Arrays.asList(R.drawable.chair2_big, R.drawable.special_offer, R.drawable.new_coll));
        break;
      case "2Chair":
        mResources = new ArrayList<>(Arrays.asList(R.drawable.chair3_big_1, R.drawable.chair3_big_2, R.drawable.chair3_big_3));
        break;
      case "0Almirah":
        mResources = new ArrayList<>(Arrays.asList(R.drawable.al1_big_1, R.drawable.al1_big_2));
        break;
      case "1Almirah":
        mResources = new ArrayList<>(Arrays.asList(R.drawable.al2_big_1, R.drawable.special_offer, R.drawable.new_coll));
        break;
      case "2Almirah":
        mResources = new ArrayList<>(Arrays.asList(R.drawable.al3_big_1, R.drawable.special_offer, R.drawable.new_coll));
        break;

    }
  }

  @Override
  public void destroyItem(ViewGroup container, int position, Object object) {
    container.removeView((LinearLayout) object);
  }
}