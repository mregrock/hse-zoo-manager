package com.hse.zoo.manager.application.dto;

import java.util.UUID;

/**
 * DTO for requesting to move an animal to a specific enclosure.
 */
public record MoveToEnclosureRequestDto(
    UUID enclosureId
) {}
 