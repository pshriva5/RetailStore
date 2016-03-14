package com.thoughtw.retail.sample.db;


import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thoughtw.retail.sample.model.CartItem;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Preference {
  public static final String RETAIL_SHARED_PREFERENCES_FILE = "retail";
  public static final String CART_ITEM = "cart";
  private SharedPreferences preferences;

  public Preference(SharedPreferences preferences) {
    this.preferences = preferences;
  }

  public ArrayList<CartItem> getCartItems(Context context) {
    String json = preferences.getString(CART_ITEM, null);
    Type type = new TypeToken<ArrayList<CartItem>>() {
    }.getType();
    return new Gson().fromJson(json, type);
  }

  public void setCartItems(Context context, ArrayList<CartItem> list) {
    preferences.edit().putString(CART_ITEM, new Gson().toJson(list)).commit();
  }


}
