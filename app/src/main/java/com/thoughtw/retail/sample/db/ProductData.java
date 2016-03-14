package com.thoughtw.retail.sample.db;

import android.content.Context;

import com.thoughtw.retail.sample.R;
import com.thoughtw.retail.sample.model.ProductModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class ProductData {
  HashMap<String, ArrayList<ProductModel>> productList;
  String[] ItemType = new String[]{"Microwave Oven", "Television", "Vacuum Cleaner", "Table", "Chair", "Almirah"};
  private ArrayList<String> itemDesceCription;
  private ArrayList<String> itemName;
  private ArrayList<String> itemPrice;
  public ArrayList<ProductModel> productItems;
  public ArrayList<ProductModel> subProductItems = new ArrayList<>();
  ArrayList<Integer> mProductImages = new ArrayList<>(Arrays.asList(R.drawable.m1,
      R.drawable.m2, R.drawable.m3,
      R.drawable.t1, R.drawable.t2, R.drawable.t3, R.drawable.v1,
      R.drawable.v2, R.drawable.v3, R.drawable.table1, R.drawable.table2, R.drawable.table3,
      R.drawable.chair1, R.drawable.chair2, R.drawable.chair3,
      R.drawable.al1, R.drawable.al2, R.drawable.al3));

  public ProductData(Context context) {
    this.productList = new HashMap<>();
    itemDesceCription = new ArrayList<>(Arrays.asList(context.getResources().getStringArray(R.array.product_description)));
    itemName = new ArrayList<>(Arrays.asList(context.getResources().getStringArray(R.array.product_names)));
    itemPrice = new ArrayList<>(Arrays.asList(context.getResources().getStringArray(R.array.product_prices)));
    productItems = new ArrayList<>();
    populateDataInHashMap();
  }

  private void populateDataInHashMap() {
    productList.clear();
    for (int j = 0; j < ItemType.length; j++) {
      for (int k = 0; k < 3; k++) {
        ProductModel productModel = new ProductModel(itemDesceCription.get(0), String.valueOf(k) + ItemType[j], mProductImages.get(0),
            Long.valueOf(itemPrice.get(0)), itemName.get(0));
        productItems.add(productModel);
        removeItemFromList();
      }
    }
  }

  private void removeItemFromList() {
    itemPrice.remove(0);
    itemName.remove(0);
    mProductImages.remove(0);
    itemDesceCription.remove(0);
  }

  public ArrayList<ProductModel> getAllProductList() {
    return this.productItems;
  }


  public ArrayList<ProductModel> getElectronicProductList() {
    subProductItems.clear();
    subProductItems.addAll(productItems.subList(0, 9));
    return subProductItems;
  }

  public ArrayList<ProductModel> getFurnitureProductList() {
    subProductItems.clear();
    subProductItems.addAll(productItems.subList(9, 18));
    return subProductItems;
  }


}
