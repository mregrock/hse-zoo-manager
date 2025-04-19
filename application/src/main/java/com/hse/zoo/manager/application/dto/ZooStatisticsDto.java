package com.hse.zoo.manager.application.dto;

import java.util.Map;

/**
 * Data Transfer Object for representing overall Zoo statistics.
 */
public record ZooStatisticsDto(
    long totalAnimals,
    long totalEnclosures,
    long availableEnclosures,
    long totalCapacity,
    long currentOccupancy,
    Map<String, Long> animalsBySpecies,
    Map<String, Long> animalsByStatus
) {} 