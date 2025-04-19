package com.hse.zoo.manager.domain.model;

public enum Gender {
  MALE("Male"),
  FEMALE("Female"),
  UNKNOWN("Unknown");

  private final String description;

  Gender(String description) {
      this.description = description;
  }

  public String getDescription() {
      return description;
  }
} 