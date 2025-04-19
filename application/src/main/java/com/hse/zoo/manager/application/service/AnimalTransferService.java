package com.hse.zoo.manager.application.service;

import java.util.UUID;

/**
 * Application service interface for handling the transfer of animals between enclosures.
 */
public interface AnimalTransferService {

  /**
   * Moves an animal to a specified enclosure.
   *
   * @param animalId    The ID of the animal to move.
   * @param newEnclosureId The ID of the target enclosure.
   * @throws // Specific exceptions for validation failures (e.g., AnimalNotFoundException,
   *         // EnclosureNotFoundException, EnclosureFullException, IncompatibleEnclosureTypeException)
   */
  void moveAnimal(UUID animalId, UUID newEnclosureId);

  /**
   * Removes an animal from its current enclosure (moves it to "outside").
   *
   * @param animalId The ID of the animal to remove from its enclosure.
   * @throws // Specific exceptions (e.g., AnimalNotFoundException, AnimalNotInEnclosureException)
   */
    void removeAnimalFromEnclosure(UUID animalId);

} 