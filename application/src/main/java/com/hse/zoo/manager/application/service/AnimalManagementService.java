package com.hse.zoo.manager.application.service;

import com.hse.zoo.manager.application.dto.AddAnimalDto;
import com.hse.zoo.manager.application.dto.AnimalDto;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Application service interface for managing Animal entities.
 * Handles operations like adding, retrieving, and deleting animals using DTOs.
 */
public interface AnimalManagementService {

  /**
   * Adds a new animal to the system based on the provided DTO.
   *
   * @param addAnimalDto DTO containing the details of the animal to add.
   * @return The DTO of the created Animal.
   */
  AnimalDto addAnimal(AddAnimalDto addAnimalDto);

  /**
   * Retrieves an animal by its ID.
   *
   * @param id The ID of the animal.
   * @return An Optional containing the Animal DTO if found, otherwise empty.
   */
  Optional<AnimalDto> getAnimalById(UUID id);

  /**
   * Retrieves all animals.
   *
   * @return A list of Animal DTOs.
   */
  List<AnimalDto> getAllAnimals();

  /**
   * Deletes an animal by its ID.
   *
   * @param id The ID of the animal to delete.
   */
  void deleteAnimal(UUID id);

} 