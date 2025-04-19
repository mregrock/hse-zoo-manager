package com.hse.zoo.manager.application.dto;

import com.hse.zoo.manager.domain.model.EnclosureType;

/**
 * Data Transfer Object for adding a new Enclosure.
 */
public record AddEnclosureDto(
    EnclosureType type,
    double size,
    int maxCapacity
) {} 