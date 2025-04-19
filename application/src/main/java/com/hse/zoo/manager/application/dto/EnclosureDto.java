package com.hse.zoo.manager.application.dto;

import com.hse.zoo.manager.domain.model.EnclosureType;
import java.util.Set;
import java.util.UUID;

/**
 * Data Transfer Object for representing Enclosure information.
 */
public record EnclosureDto(
    UUID id,
    EnclosureType type,
    double size,
    int maxCapacity,
    int currentAnimalCount,
    Set<UUID> animalIds // Optionally include the list of animal IDs
) {} 