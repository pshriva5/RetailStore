package com.thoughtw.retail.sample.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor(suppressConstructorProperties = true)
public class CartItem implements Serializable {
  private String description;
  private String imageUrl;
  private int imagePrimary;
  private String itemId;
  private long price;
  private int quantity;
  private String title;
  private Long subTotal;
  private Long regularPrice;

  public CartItem(String description, String itemId, int imagePrimary, Long price, String title, int quantity, Long subTotal, Long regularPrice) {
    this.description = description;
    this.itemId = itemId;
    this.imagePrimary = imagePrimary;
    this.price = price;
    this.title = title;
    this.quantity = quantity;
    this.subTotal = subTotal;
    this.regularPrice = regularPrice;
  }
}