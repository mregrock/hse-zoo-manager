package com.hse.zoo.manager.application.dto;

import com.hse.zoo.manager.domain.model.FoodType;
import com.hse.zoo.manager.domain.model.Gender;
import java.time.LocalDate;

/**
 * Data Transfer Object for adding a new Animal.
 */
public record AddAnimalDto(
    String species,
    String name,
    LocalDate dateOfBirth,
    Gender gender,
    FoodType favoriteFood
) {} 