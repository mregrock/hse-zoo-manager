package com.hse.zoo.manager.application.service;

import com.hse.zoo.manager.application.dto.AddFeedingScheduleDto;
import com.hse.zoo.manager.application.dto.FeedingScheduleDto;
import java.util.List;
import java.util.UUID;

/**
 * Application service interface for organizing the feeding process.
 */
public interface FeedingOrganizationService {

  /**
   * Adds a new feeding task to the schedule.
   *
   * @param addDto DTO containing the details for the new schedule entry.
   * @return The DTO of the created Feeding Schedule entry.
   */
  FeedingScheduleDto addFeedingSchedule(AddFeedingScheduleDto addDto);

  /**
   * Retrieves all feeding schedule entries.
   *
   * @return A list of all Feeding Schedule DTOs.
   */
  List<FeedingScheduleDto> getAllFeedingSchedules();

  /**
   * Retrieves all feeding schedule entries for a specific animal.
   *
   * @param animalId The ID of the animal.
   * @return A list of Feeding Schedule DTOs for the specified animal.
   */
  List<FeedingScheduleDto> getFeedingSchedulesForAnimal(UUID animalId);

  /**
   * Marks a specific feeding task as completed.
   *
   * @param scheduleId The ID of the feeding schedule entry to mark as complete.
   */
  void markFeedingAsCompleted(UUID scheduleId);

} 