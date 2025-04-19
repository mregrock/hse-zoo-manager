package com.hse.zoo.manager.application.service.impl;

import com.hse.zoo.manager.application.dto.AddAnimalDto;
import com.hse.zoo.manager.application.dto.AnimalDto;
import com.hse.zoo.manager.application.service.AnimalManagementService;
import com.hse.zoo.manager.domain.model.Animal;
import com.hse.zoo.manager.domain.repository.AnimalRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AnimalManagementServiceImpl implements AnimalManagementService {

private final AnimalRepository animalRepository;

@Override
@Transactional
public AnimalDto addAnimal(AddAnimalDto addAnimalDto) {
  Animal newAnimal = Animal.createNew(
      addAnimalDto.species(),
      addAnimalDto.name(),
      addAnimalDto.dateOfBirth(),
      addAnimalDto.gender(),
      addAnimalDto.favoriteFood()
  );
  Animal savedAnimal = animalRepository.save(newAnimal);
  return mapToAnimalDto(savedAnimal);
}

@Override
@Transactional(readOnly = true)
public Optional<AnimalDto> getAnimalById(UUID id) {
  return animalRepository.findById(id).map(this::mapToAnimalDto);
}

@Override
@Transactional(readOnly = true)
public List<AnimalDto> getAllAnimals() {
  return animalRepository.findAll().stream()
      .map(this::mapToAnimalDto)
      .collect(Collectors.toList());
}

@Override
@Transactional
public void deleteAnimal(UUID id) {
  animalRepository.deleteById(id);
}

private AnimalDto mapToAnimalDto(Animal animal) {
  return new AnimalDto(
      animal.getId(),
      animal.getSpecies(),
      animal.getName(),
      animal.getDateOfBirth(),
      animal.getGender(),
      animal.getFavoriteFood(),
      animal.getStatus(),
      animal.getEnclosureId()
  );
}
}
