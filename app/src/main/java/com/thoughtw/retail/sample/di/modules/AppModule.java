package com.thoughtw.retail.sample.di.modules;

import android.content.Context;
import android.content.SharedPreferences;

import com.thoughtw.retail.sample.App;
import com.thoughtw.retail.sample.db.Preference;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

  private App app;

  public AppModule(App app) {
    this.app = app;
  }

  @Provides
  @Singleton
  public App application() {
    return this.app;
  }

  @Provides
  @Singleton
  public Context context() {
    return this.application();
  }

//  @Provides
//  @Singleton
//  SharedPreferences provideSharedPreferences() {
//    return app.getSharedPreferences(Preference.RETAIL_SHARED_PREFERENCES_FILE, Context.MODE_PRIVATE);
//  }

}
