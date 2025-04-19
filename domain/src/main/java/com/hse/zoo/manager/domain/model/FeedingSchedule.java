package com.hse.zoo.manager.domain.model;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FeedingSchedule {

  private final UUID id;
  private final UUID animalId;
  private LocalDateTime feedingTime;
  private FoodType foodType;
  private boolean completed;

  /**
   * Factory method to create a new FeedingSchedule entry.
   *
   * @param animalId    The ID of the animal.
   * @param feedingTime The scheduled time for feeding.
   * @param foodType    The type of food to be given.
   * @return A new FeedingSchedule instance.
   * @throws IllegalArgumentException if any argument is null.
   */
  public static FeedingSchedule createNew(UUID animalId, LocalDateTime feedingTime, FoodType foodType) {
    Objects.requireNonNull(animalId, "Animal ID cannot be null");
    Objects.requireNonNull(feedingTime, "Feeding time cannot be null");
    Objects.requireNonNull(foodType, "Food type cannot be null");

    return new FeedingSchedule(UUID.randomUUID(), animalId, feedingTime, foodType, false);
  }

  /**
   * Marks this feeding task as completed.
   *
   * @throws IllegalStateException if the task was already completed.
   */
  public void markAsCompleted() {
    if (this.completed) {
      throw new IllegalStateException("Feeding task " + id + " is already completed.");
    }
    this.completed = true;
    System.out.println(
        "Feeding task " + id + " for animal " + animalId + " marked as completed.");
  }

  /**
   * Changes the scheduled feeding time.
   *
   * @param newTime The new time for feeding.
   * @throws IllegalArgumentException if the new time is null.
   * @throws IllegalStateException if the task is already completed.
   */
  public void changeTime(LocalDateTime newTime) {
    Objects.requireNonNull(newTime, "New feeding time cannot be null");
    if (this.completed) {
      throw new IllegalStateException("Cannot change time for a completed feeding task " + id);
    }
    this.feedingTime = newTime;
    System.out.println(
        "Feeding task " + id + " for animal " + animalId + " rescheduled to " + newTime);
  }

  @Override
  public String toString() {
    return "FeedingSchedule{" +
        "id=" + id +
        ", animalId=" + animalId +
        ", feedingTime=" + feedingTime +
        ", foodType=" + foodType.getDescription() +
        ", completed=" + completed +
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
    FeedingSchedule that = (FeedingSchedule) o;
    return id.equals(that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
} 