package com.hse.zoo.manager.domain.model;

public enum EnclosureType {
  PREDATOR("Predator Enclosure"),
  HERBIVORE("Herbivore Enclosure"),
  AVIARY("Aviary"),
  AQUARIUM("Aquarium"),
  GENERAL("General Purpose");

  private final String description;

  EnclosureType(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }
} 