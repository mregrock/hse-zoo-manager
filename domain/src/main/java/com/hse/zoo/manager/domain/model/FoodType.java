package com.hse.zoo.manager.domain.model;

public enum FoodType {
  MEAT("Meat"),
  FISH("Fish"),
  PLANTS("Plants"),
  FRUITS("Fruits"),
  VEGETABLES("Vegetables"),
  SEEDS("Seeds"),
  INSECTS("Insects"),
  SPECIAL_FEED("Special Feed");

  private final String description;

  FoodType(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }
} 