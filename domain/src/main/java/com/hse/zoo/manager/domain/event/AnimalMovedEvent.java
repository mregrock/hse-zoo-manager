package com.hse.zoo.manager.domain.event;

import java.time.Instant;
import java.util.UUID;
import lombok.Getter;

/**
 * Domain Event indicating that an animal has been moved between enclosures (or into/out of one).
 */
@Getter
public class AnimalMovedEvent {

  private final UUID eventId;
  private final Instant occurredOn;
  private final UUID animalId;
  private final UUID previousEnclosureId;
  private final UUID newEnclosureId;

  /**
   * Constructs a new AnimalMovedEvent.
   *
   * @param animalId            The ID of the animal that moved.
   * @param previousEnclosureId The ID of the enclosure the animal came from (null if none).
   * @param newEnclosureId      The ID of the enclosure the animal moved to (null if none).
   */
  public AnimalMovedEvent(UUID animalId, UUID previousEnclosureId, UUID newEnclosureId) {
    this.eventId = UUID.randomUUID();
    this.occurredOn = Instant.now();
    this.animalId = animalId;
    this.previousEnclosureId = previousEnclosureId;
    this.newEnclosureId = newEnclosureId;
  }

} 