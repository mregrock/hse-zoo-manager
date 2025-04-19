package com.hse.zoo.manager.domain.model;

public enum AnimalStatus {
  HEALTHY("Healthy"),
  SICK("Sick"),
  UNDER_TREATMENT("Under Treatment");

  private final String description;

  AnimalStatus(String description) {
      this.description = description;
  }

  public String getDescription() {
      return description;
  }
} 