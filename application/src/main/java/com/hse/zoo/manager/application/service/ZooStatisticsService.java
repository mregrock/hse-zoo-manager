package com.hse.zoo.manager.application.service;

import com.hse.zoo.manager.application.dto.ZooStatisticsDto;

/**
 * Application service interface for retrieving Zoo statistics.
 */
public interface ZooStatisticsService {

  /**
   * Gathers and returns various statistics about the zoo.
   *
   * @return A DTO containing zoo statistics.
   */
  ZooStatisticsDto getStatistics();

}
 