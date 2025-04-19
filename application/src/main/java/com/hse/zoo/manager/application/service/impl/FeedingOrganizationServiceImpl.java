package com.hse.zoo.manager.application.service.impl;

import com.hse.zoo.manager.application.dto.AddFeedingScheduleDto;
import com.hse.zoo.manager.application.dto.FeedingScheduleDto;
import com.hse.zoo.manager.application.service.FeedingOrganizationService;
import com.hse.zoo.manager.domain.model.Animal;
import com.hse.zoo.manager.domain.model.FeedingSchedule;
import com.hse.zoo.manager.domain.repository.AnimalRepository;
import com.hse.zoo.manager.domain.repository.FeedingScheduleRepository;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FeedingOrganizationServiceImpl implements FeedingOrganizationService {

  private final FeedingScheduleRepository feedingScheduleRepository;
  private final AnimalRepository animalRepository;

  @Override
  @Transactional
  public FeedingScheduleDto addFeedingSchedule(AddFeedingScheduleDto addDto) {
    animalRepository.findById(addDto.animalId())
        .orElseThrow(() -> new RuntimeException("Animal not found: " + addDto.animalId()));

    FeedingSchedule newSchedule = FeedingSchedule.createNew(
        addDto.animalId(),
        addDto.feedingTime(),
        addDto.foodType()
    );
    FeedingSchedule savedSchedule = feedingScheduleRepository.save(newSchedule);
    return mapToFeedingScheduleDto(savedSchedule);
  }

  @Override
  @Transactional(readOnly = true)
  public List<FeedingScheduleDto> getAllFeedingSchedules() {
    return feedingScheduleRepository.findAll().stream()
        .map(this::mapToFeedingScheduleDto)
        .collect(Collectors.toList());
  }

  @Override
  @Transactional(readOnly = true)
  public List<FeedingScheduleDto> getFeedingSchedulesForAnimal(UUID animalId) {
    animalRepository.findById(animalId)
         .orElseThrow(() -> new RuntimeException("Animal not found: " + animalId));

    return feedingScheduleRepository.findByAnimalId(animalId).stream()
        .map(this::mapToFeedingScheduleDto)
        .collect(Collectors.toList());
  }

  @Override
  @Transactional
  public void markFeedingAsCompleted(UUID scheduleId) {
    FeedingSchedule schedule = feedingScheduleRepository.findById(scheduleId)
        .orElseThrow(() -> new RuntimeException("Feeding schedule not found: " + scheduleId));

    schedule.markAsCompleted();
    feedingScheduleRepository.save(schedule);
  }

  private FeedingScheduleDto mapToFeedingScheduleDto(FeedingSchedule schedule) {
    String animalName = animalRepository.findById(schedule.getAnimalId())
        .map(Animal::getName)
        .orElse("Unknown Animal");

    return new FeedingScheduleDto(
        schedule.getId(),
        schedule.getAnimalId(),
        animalName,
        schedule.getFeedingTime(),
        schedule.getFoodType(),
        schedule.isCompleted()
    );
  }
}
