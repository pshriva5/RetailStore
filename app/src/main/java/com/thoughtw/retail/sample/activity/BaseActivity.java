package com.thoughtw.retail.sample.activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.thoughtw.retail.sample.App;
import com.thoughtw.retail.sample.di.components.AppComponent;
import com.thoughtw.retail.sample.di.modules.ActivityModule;
import com.thoughtw.retail.sample.navigation.Navigator;

import javax.inject.Inject;


public abstract class BaseActivity extends AppCompatActivity {

  @Inject
  Navigator navigator;

  public App mApp;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.getApplicationComponent().inject(this);
    mApp = getApplicationComponent().application();
  }

  protected void addFragment(int containerViewId, Fragment fragment) {
    FragmentTransaction fragmentTransaction = this.getFragmentManager().beginTransaction();
    fragmentTransaction.add(containerViewId, fragment);
    fragmentTransaction.commit();
  }


  protected AppComponent getApplicationComponent() {
    return ((App) getApplication()).getApplicationComponent();
  }


  protected ActivityModule getActivityModule() {
    return new ActivityModule(this);
  }
}