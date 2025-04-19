package com.hse.zoo.manager.infrastructure.repository.inmemory;

import com.hse.zoo.manager.domain.model.FeedingSchedule;
import com.hse.zoo.manager.domain.repository.FeedingScheduleRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryFeedingScheduleRepository implements FeedingScheduleRepository {

  private final Map<UUID, FeedingSchedule> schedules = new ConcurrentHashMap<>();

  @Override
  public Optional<FeedingSchedule> findById(UUID id) {
    return Optional.ofNullable(schedules.get(id));
  }

  @Override
  public List<FeedingSchedule> findAll() {
    return new ArrayList<>(schedules.values());
  }

  @Override
  public FeedingSchedule save(FeedingSchedule schedule) {
    schedules.put(schedule.getId(), schedule);
    return schedule;
  }

  @Override
  public void deleteById(UUID id) {
    schedules.remove(id);
  }

  @Override
  public List<FeedingSchedule> findByAnimalId(UUID animalId) {
    return schedules.values().stream()
        .filter(schedule -> schedule.getAnimalId().equals(animalId))
        .collect(Collectors.toList());
  }

  @Override
  public List<FeedingSchedule> findUpcomingFeedings(LocalDateTime time) {
    return schedules.values().stream()
        .filter(schedule -> !schedule.isCompleted() && schedule.getFeedingTime().isBefore(time))
        .collect(Collectors.toList());
  }
}
 