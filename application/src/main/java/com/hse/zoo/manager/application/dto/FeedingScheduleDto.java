package com.hse.zoo.manager.application.dto;

import com.hse.zoo.manager.domain.model.FoodType;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Data Transfer Object for representing Feeding Schedule information.
 */
public record FeedingScheduleDto(
    UUID id,
    UUID animalId,
    String animalName,
    LocalDateTime feedingTime,
    FoodType foodType,
    boolean completed
) {} 