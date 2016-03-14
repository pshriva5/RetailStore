package com.thoughtw.retail.sample.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor(suppressConstructorProperties = true)
public class ProductModel implements Serializable {
  private String description;
  private String entityId;
  private Long finalPrice;
  private String imageUrl;
  private String name;
  private int primaryImage;
  private int regularPrice;


  public ProductModel(String description, String itemId, int imagePrimary, Long price, String title) {
    this.description = description;
    this.entityId = itemId;
    this.primaryImage = imagePrimary;
    this.finalPrice = price;
    this.name = title;
  }
}