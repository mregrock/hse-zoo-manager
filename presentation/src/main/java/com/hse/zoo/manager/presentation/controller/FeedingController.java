package com.hse.zoo.manager.presentation.controller;

import com.hse.zoo.manager.application.dto.AddFeedingScheduleDto;
import com.hse.zoo.manager.application.dto.FeedingScheduleDto;
import com.hse.zoo.manager.application.service.FeedingOrganizationService;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * REST controller for managing feeding schedules.
 */
@RestController
@RequestMapping("/api/feeding-schedules")
@RequiredArgsConstructor
public class FeedingController {

  private final FeedingOrganizationService feedingOrganizationService;

  /**
   * POST /api/feeding-schedules : Add a new feeding schedule entry.
   */
  @PostMapping
  public ResponseEntity<FeedingScheduleDto> addFeedingSchedule(
      @RequestBody AddFeedingScheduleDto addDto) {
    try {
        FeedingScheduleDto createdSchedule = feedingOrganizationService.addFeedingSchedule(addDto);
        URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(createdSchedule.id())
            .toUri();
        return ResponseEntity.created(location).body(createdSchedule);
    } catch (RuntimeException e) {
        return ResponseEntity.badRequest().body(null);
    }
  }

  /**
   * GET /api/feeding-schedules : Get all feeding schedules or filter by animal ID.
   */
  @GetMapping
  public List<FeedingScheduleDto> getFeedingSchedules(
      @RequestParam(required = false) UUID animalId) {
    if (animalId != null) {
      return feedingOrganizationService.getFeedingSchedulesForAnimal(animalId);
    } else {
      return feedingOrganizationService.getAllFeedingSchedules();
    }
  }

   /**
   * POST /api/feeding-schedules/{scheduleId}/complete : Mark a feeding task as completed.
   */
  @PostMapping("/{scheduleId}/complete")
  public ResponseEntity<Void> markFeedingAsCompleted(@PathVariable UUID scheduleId) {
     try {
      feedingOrganizationService.markFeedingAsCompleted(scheduleId);
      return ResponseEntity.ok().build();
    } catch (IllegalStateException e) {
        return ResponseEntity.badRequest().body(null);
    } catch (RuntimeException e) {
         return ResponseEntity.notFound().build();
    }
  }
}
