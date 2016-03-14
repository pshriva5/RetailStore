package com.thoughtw.retail.sample.di.modules;

import com.thoughtw.retail.sample.db.Preference;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class PreferenceModule {
  private final Preference preference;

  public PreferenceModule(Preference preference) {
    this.preference = preference;
  }


  @Provides
  @Singleton
  Preference providePreferences() {
    return this.preference;
  }

}
