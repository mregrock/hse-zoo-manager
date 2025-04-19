package com.hse.zoo.manager.application.dto;

import com.hse.zoo.manager.domain.model.FoodType;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Data Transfer Object for adding a new Feeding Schedule entry.
 */
public record AddFeedingScheduleDto(
    UUID animalId,
    LocalDateTime feedingTime,
    FoodType foodType
) {} 