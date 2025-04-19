package com.hse.zoo.manager.application.dto;

import com.hse.zoo.manager.domain.model.AnimalStatus;
import com.hse.zoo.manager.domain.model.FoodType;
import com.hse.zoo.manager.domain.model.Gender;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Data Transfer Object for representing Animal information.
 */
public record AnimalDto(
    UUID id,
    String species,
    String name,
    LocalDate dateOfBirth,
    Gender gender,
    FoodType favoriteFood,
    AnimalStatus status,
    UUID enclosureId // Can be null
) {} 