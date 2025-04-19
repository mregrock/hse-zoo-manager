package com.hse.zoo.manager.domain.repository;

import com.hse.zoo.manager.domain.model.Enclosure;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Interface for data access operations related to Enclosure entities.
 * Defines the contract for managing enclosure persistence.
 */
public interface EnclosureRepository {

  /**
   * Finds an Enclosure by its unique identifier.
   *
   * @param id The UUID of the enclosure.
   * @return An Optional containing the found enclosure, or empty if not found.
   */
  Optional<Enclosure> findById(UUID id);

  /**
   * Retrieves all Enclosure entities.
   *
   * @return A list of all enclosures.
   */
  List<Enclosure> findAll();

  /**
   * Saves an Enclosure entity (either creates a new one or updates an existing one).
   *
   * @param enclosure The enclosure entity to save.
   * @return The saved enclosure entity.
   */
  Enclosure save(Enclosure enclosure);

  /**
   * Deletes an Enclosure by its unique identifier.
   *
   * @param id The UUID of the enclosure to delete.
   */
  void deleteById(UUID id);

} 