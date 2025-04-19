package com.hse.zoo.manager.domain.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Enclosure {

  private final UUID id;
  private EnclosureType type;
  private double size;
  private final int maxCapacity;
  private final Set<UUID> animalIds;

  /**
   * Factory method to create a new Enclosure.
   *
   * @param type        The type of the enclosure.
   * @param size        The size in square meters.
   * @param maxCapacity The maximum number of animals it can hold.
   * @return A new Enclosure instance.
   * @throws IllegalArgumentException if size or maxCapacity are invalid.
   */
  public static Enclosure createNew(EnclosureType type, double size, int maxCapacity) {
    if (size <= 0) {
      throw new IllegalArgumentException("Size must be positive.");
    }
    if (maxCapacity <= 0) {
      throw new IllegalArgumentException("Maximum capacity must be positive.");
    }
    return new Enclosure(UUID.randomUUID(), type, size, maxCapacity, new HashSet<>());
  }

  /**
   * Checks if an animal can be added based on current capacity.
   * Does not check for type compatibility yet.
   *
   * @return true if there is space, false otherwise.
   */
  public boolean canAddAnimal() {
    return this.animalIds.size() < this.maxCapacity;
  }

  /**
   * Internal method to add an animal's ID to the enclosure.
   *
   * @param animalId The ID of the animal to add.
   * @throws IllegalStateException if the enclosure is full or the animal is already inside.
   */
  void addAnimalInternal(UUID animalId) {
    Objects.requireNonNull(animalId, "Animal ID cannot be null");
    if (!canAddAnimal()) {
      throw new IllegalStateException("Enclosure with id " + id + " is full.");
    }
    if (!this.animalIds.add(animalId)) {
        throw new IllegalStateException(
            "Animal with id " + animalId + " is already in enclosure " + id);
    }
    System.out.println("Animal " + animalId + " added to enclosure " + id);
  }

  /**
   * Internal method to remove an animal's ID from the enclosure.
   *
   * @param animalId The ID of the animal to remove.
   * @throws IllegalStateException if the animal is not found in this enclosure.
   */
  void removeAnimalInternal(UUID animalId) {
     Objects.requireNonNull(animalId, "Animal ID cannot be null");
    if (!this.animalIds.remove(animalId)) {
      throw new IllegalStateException(
          "Animal with id " + animalId + " not found in enclosure " + id);
    }
     System.out.println("Animal " + animalId + " removed from enclosure " + id);
  }

  /**
   * Simulates cleaning the enclosure.
   */
  public void clean() {
    System.out.println("Cleaning enclosure " + id + " (Type: " + type.getDescription() + ").");
  }

  /**
   * Gets the current number of animals in the enclosure.
   *
   * @return The count of animals.
   */
  public int getCurrentAnimalCount() {
    return this.animalIds.size();
  }

  @Override
  public String toString() {
    return "Enclosure{" +
        "id=" + id +
        ", type=" + type.getDescription() +
        ", size=" + size +
        ", maxCapacity=" + maxCapacity +
        ", currentAnimalCount=" + getCurrentAnimalCount() +
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
    Enclosure enclosure = (Enclosure) o;
    return id.equals(enclosure.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
} 