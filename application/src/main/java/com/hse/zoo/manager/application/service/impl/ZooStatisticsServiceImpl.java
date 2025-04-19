package com.hse.zoo.manager.application.service.impl;

import com.hse.zoo.manager.application.dto.ZooStatisticsDto;
import com.hse.zoo.manager.application.service.ZooStatisticsService;
import com.hse.zoo.manager.domain.model.Animal;
import com.hse.zoo.manager.domain.model.Enclosure;
import com.hse.zoo.manager.domain.repository.AnimalRepository;
import com.hse.zoo.manager.domain.repository.EnclosureRepository;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ZooStatisticsServiceImpl implements ZooStatisticsService {

  private final AnimalRepository animalRepository;
  private final EnclosureRepository enclosureRepository;

  @Override
  @Transactional(readOnly = true)
  public ZooStatisticsDto getStatistics() {
    List<Animal> allAnimals = animalRepository.findAll();
    List<Enclosure> allEnclosures = enclosureRepository.findAll();

    long totalAnimals = allAnimals.size();
    long totalEnclosures = allEnclosures.size();

    long availableEnclosures = allEnclosures.stream()
        .filter(Enclosure::canAddAnimal)
        .count();

    long totalCapacity = allEnclosures.stream()
        .mapToLong(Enclosure::getMaxCapacity)
        .sum();

    long currentOccupancy = allEnclosures.stream()
        .mapToLong(Enclosure::getCurrentAnimalCount)
        .sum();

    Map<String, Long> animalsBySpecies = allAnimals.stream()
        .collect(Collectors.groupingBy(Animal::getSpecies, Collectors.counting()));

    Map<String, Long> animalsByStatus = allAnimals.stream()
        .collect(Collectors.groupingBy(animal -> animal.getStatus().getDescription(), 
                                      Collectors.counting()));

    return new ZooStatisticsDto(
      totalAnimals,
      totalEnclosures,
      availableEnclosures,
      totalCapacity,
      currentOccupancy,
      animalsBySpecies,
      animalsByStatus
    );
  }
}
