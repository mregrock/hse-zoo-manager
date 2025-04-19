package com.hse.zoo.manager.domain.model;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents an Animal entity.
 * This is an Aggregate Root for the animal domain.
 */
@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Animal {

  private UUID id;
  private String species;
  private String name;
  private LocalDate dateOfBirth;
  private Gender gender;
  private FoodType favoriteFood;

  @Setter(AccessLevel.PACKAGE)
  private AnimalStatus status;

  @Setter(AccessLevel.PACKAGE)
  private UUID enclosureId;

  /**
   * Factory method to create a new Animal.
   *
   * @param species      The species of the animal.
   * @param name         The name/nickname of the animal.
   * @param dateOfBirth  The date of birth.
   * @param gender       The gender.
   * @param favoriteFood The animal's favorite food type.
   * @return A new Animal instance.
   * @throws IllegalArgumentException if species, name, or dateOfBirth are invalid.
   */
  public static Animal createNew(
    String species, String name, LocalDate dateOfBirth, Gender gender, FoodType favoriteFood) {
      if (species == null || species.isBlank()) {
        throw new IllegalArgumentException("Species cannot be empty.");
      }
      if (name == null || name.isBlank()) {
        throw new IllegalArgumentException("Name cannot be empty.");
      }
      if (dateOfBirth == null || dateOfBirth.isAfter(LocalDate.now())) {
        throw new IllegalArgumentException("Invalid date of birth.");
      }
      if (favoriteFood == null) {
        throw new IllegalArgumentException("Favorite food cannot be null.");
      }
      return new Animal(
              UUID.randomUUID(),
              species,
              name,
              dateOfBirth,
              gender,
              favoriteFood,
              AnimalStatus.HEALTHY,
              null);
    }

  /**
   * Feeds the animal.
   *
   * @param food The type of food given.
   */
  public void feed(FoodType food) {
    System.out.println(
            "Feeding "
                    + name
                    + " (" + species + ") with "
                    + food.getDescription()
                    + ". Favorite food: "
                    + favoriteFood.getDescription());
    if (status == AnimalStatus.SICK) {
      System.out.println(name + " is still sick, but ate.");
    } else {
      this.status = AnimalStatus.HEALTHY;
    }
  }

  public void treat() {
    System.out.println("Treating " + name + " (" + species + ").");
    if (this.status != AnimalStatus.HEALTHY) {
      this.status = AnimalStatus.UNDER_TREATMENT;
      System.out.println(name + " is now under treatment.");
    } else {
      System.out.println(name + " is already healthy.");
    }
  }

  public void markAsHealthy() {
    if (this.status == AnimalStatus.UNDER_TREATMENT || this.status == AnimalStatus.SICK) {
      this.status = AnimalStatus.HEALTHY;
      System.out.println(name + " has recovered!");
    } else {
      System.out.println(name + " was already healthy.");
    }
  }

  /**
   * Internal method to move the animal to a new enclosure.
   *
   * @param newEnclosureId The ID of the new enclosure.
   * @return The ID of the previous enclosure (can be null).
   */
  public UUID moveToEnclosureInternal(UUID newEnclosureId) {
    System.out.println("Moving " + name + " to enclosure " + newEnclosureId);
    UUID oldEnclosureId = this.enclosureId;
    this.enclosureId = newEnclosureId;
    return oldEnclosureId;
  }

  /**
   * Internal method to remove the animal from its current enclosure.
   */
  public void removeFromEnclosureInternal() {
    if (this.enclosureId != null) {
      System.out.println("Removing " + name + " from enclosure " + this.enclosureId);
      this.enclosureId = null;
    } else {
      System.out.println(name + " is not in any enclosure.");
    }
  }

  @Override
  public String toString() {
    return "Animal{" +
            "id=" + id +
            ", species='" + species + '\'' +
            ", name='" + name + '\'' +
            ", dateOfBirth=" + dateOfBirth +
            ", gender=" + gender +
            ", favoriteFood=" + favoriteFood.getDescription() +
            ", status=" + status.getDescription() +
            ", enclosureId=" + enclosureId +
            '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Animal animal = (Animal) o;
    return id.equals(animal.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
} 