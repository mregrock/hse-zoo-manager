package com.hse.zoo.manager.presentation.controller;

import com.hse.zoo.manager.application.dto.ZooStatisticsDto;
import com.hse.zoo.manager.application.service.ZooStatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for retrieving zoo statistics.
 */
@RestController
@RequestMapping("/api/statistics")
@RequiredArgsConstructor
public class StatisticsController {

  private final ZooStatisticsService zooStatisticsService;

  /**
   * GET /api/statistics : Get overall zoo statistics.
   */
  @GetMapping
  public ResponseEntity<ZooStatisticsDto> getStatistics() {
    ZooStatisticsDto statistics = zooStatisticsService.getStatistics();
    return ResponseEntity.ok(statistics);
  }
}
 