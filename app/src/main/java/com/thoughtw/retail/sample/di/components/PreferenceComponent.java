package com.thoughtw.retail.sample.di.components;

import com.thoughtw.retail.sample.App;
import com.thoughtw.retail.sample.db.Preference;
import com.thoughtw.retail.sample.di.modules.PreferenceModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {PreferenceModule.class})
public interface PreferenceComponent {
  Preference providePreferences();

  void inject(App app);
}
