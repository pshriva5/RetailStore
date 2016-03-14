package com.thoughtw.retail.sample.di.components;


import com.thoughtw.retail.sample.App;
import com.thoughtw.retail.sample.activity.MainActivity;
import com.thoughtw.retail.sample.db.Preference;
import com.thoughtw.retail.sample.db.ProductData;
import com.thoughtw.retail.sample.di.modules.DbModule;
import com.thoughtw.retail.sample.di.modules.PreferenceModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {DbModule.class})
public interface DbComponent {

  ProductData provideProductData();

  void inject(App app);

  void inject(MainActivity mainActivity);

}
