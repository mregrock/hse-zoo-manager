package com.hse.zoo.manager.domain.repository;

import com.hse.zoo.manager.domain.model.FeedingSchedule;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Interface for data access operations related to FeedingSchedule entities.
 * Defines the contract for managing feeding schedule persistence.
 */
public interface FeedingScheduleRepository {

  /**
   * Finds a FeedingSchedule by its unique identifier.
   *
   * @param id The UUID of the feeding schedule entry.
   * @return An Optional containing the found entry, or empty if not found.
   */
  Optional<FeedingSchedule> findById(UUID id);

  /**
   * Retrieves all FeedingSchedule entities.
   *
   * @return A list of all feeding schedule entries.
   */
  List<FeedingSchedule> findAll();

  /**
   * Saves a FeedingSchedule entity (either creates a new one or updates an existing one).
   *
   * @param schedule The feeding schedule entity to save.
   * @return The saved feeding schedule entity.
   */
  FeedingSchedule save(FeedingSchedule schedule);

  /**
   * Deletes a FeedingSchedule by its unique identifier.
   *
   * @param id The UUID of the feeding schedule entry to delete.
   */
  void deleteById(UUID id);

  /**
   * Finds all feeding schedules for a specific animal.
   *
   * @param animalId The UUID of the animal.
   * @return A list of feeding schedules for the given animal.
   */
  List<FeedingSchedule> findByAnimalId(UUID animalId);

   /**
   * Finds all upcoming (not completed) feeding schedules before a certain time.
   *
   * @param time The time threshold.
   * @return A list of upcoming feeding schedules.
   */
  List<FeedingSchedule> findUpcomingFeedings(LocalDateTime time);

} 