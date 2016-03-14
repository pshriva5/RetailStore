package com.thoughtw.retail.sample.di.modules;

import com.thoughtw.retail.sample.db.ProductData;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DbModule {
  private final ProductData productData;


  public DbModule(ProductData productData) {
    this.productData = productData;

  }

  @Provides
  @Singleton
  public ProductData provideProductData() {
    return this.productData;
  }


}
