package com.hse.zoo.manager.domain.repository;

import com.hse.zoo.manager.domain.model.Animal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Interface for data access operations related to Animal entities.
 * Defines the contract for managing animal persistence.
 */
public interface AnimalRepository {

  /**
   * Finds an Animal by its unique identifier.
   *
   * @param id The UUID of the animal.
   * @return An Optional containing the found animal, or empty if not found.
   */
  Optional<Animal> findById(UUID id);

  /**
   * Retrieves all Animal entities.
   *
   * @return A list of all animals.
   */
  List<Animal> findAll();

  /**
   * Saves an Animal entity (either creates a new one or updates an existing one).
   *
   * @param animal The animal entity to save.
   * @return The saved animal entity (possibly with updated state like generated ID).
   */
  Animal save(Animal animal);

  /**
   * Deletes an Animal by its unique identifier.
   *
   * @param id The UUID of the animal to delete.
   */
  void deleteById(UUID id);

  // Add other specific query methods if needed, e.g.:
  // List<Animal> findBySpecies(String species);
  // List<Animal> findByEnclosureId(UUID enclosureId);
} 