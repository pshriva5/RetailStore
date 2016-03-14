
package com.thoughtw.retail.sample.navigation;

import android.content.Context;
import android.content.Intent;

import com.thoughtw.retail.sample.activity.ViewCartActivity;
import com.thoughtw.retail.sample.activity.ViewProductDetails;
import com.thoughtw.retail.sample.model.ProductModel;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Class used to navigate through the application.
 */
@Singleton
public class Navigator {

  @Inject
  public Navigator() {
    //empty
  }
  public void navigateToViewCartDetails(Context context) {
    if (context != null) {
      Intent intentToLaunch = ViewCartActivity.getCallingIntent(context);
      context.startActivity(intentToLaunch);
    }
  }
  public void navigateToProductDetails(Context context, ProductModel item) {
    if (context != null) {
      Intent intentToLaunch = ViewProductDetails.getCallingIntent(context, item);
      context.startActivity(intentToLaunch);
    }
  }
}
