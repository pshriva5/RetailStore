package com.thoughtw.retail.sample.di.components;

import android.content.Context;
import android.content.SharedPreferences;

import com.thoughtw.retail.sample.App;
import com.thoughtw.retail.sample.activity.BaseActivity;
import com.thoughtw.retail.sample.activity.ViewProductDetails;
import com.thoughtw.retail.sample.di.modules.AppModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
  void inject(App application);

  void inject(BaseActivity baseActivity);

  void inject(ViewProductDetails viewProductDetails);


  App application();


  Context context();



}
