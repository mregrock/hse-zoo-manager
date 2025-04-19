package com.hse.zoo.manager.domain.event;

import com.hse.zoo.manager.domain.model.FoodType;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;

/**
 * Domain Event indicating that it is time to feed a specific animal according to the schedule.
 */
@Getter
public class FeedingTimeEvent {

  private final UUID eventId;
  private final Instant occurredOn;
  private final UUID scheduleId;
  private final UUID animalId;
  private final LocalDateTime scheduledTime;
  private final FoodType foodType;

  /**
   * Constructs a new FeedingTimeEvent.
   *
   * @param scheduleId    The ID of the corresponding FeedingSchedule entry.
   * @param animalId      The ID of the animal to be fed.
   * @param scheduledTime The time the feeding was scheduled for.
   * @param foodType      The type of food to be given.
   */
  public FeedingTimeEvent(UUID scheduleId, UUID animalId, LocalDateTime scheduledTime, FoodType foodType) {
    this.eventId = UUID.randomUUID();
    this.occurredOn = Instant.now(); // Or maybe the time the event was triggered
    this.scheduleId = scheduleId;
    this.animalId = animalId;
    this.scheduledTime = scheduledTime;
    this.foodType = foodType;
  }

} 