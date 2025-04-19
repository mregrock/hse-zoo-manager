package com.hse.zoo.manager.presentation.controller;

import com.hse.zoo.manager.application.dto.AddAnimalDto;
import com.hse.zoo.manager.application.dto.AnimalDto;
import com.hse.zoo.manager.application.service.AnimalManagementService;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * REST controller for managing animals.
 * Handles HTTP requests related to animal operations.
 */
@RestController
@RequestMapping("/api/animals")
@RequiredArgsConstructor
public class AnimalController {

  private final AnimalManagementService animalManagementService;

  /**
   * GET /api/animals : Get all animals.
   *
   * @return A list of all animals.
   */
  @GetMapping
  public List<AnimalDto> getAllAnimals() {
    return animalManagementService.getAllAnimals();
  }

  /**
   * GET /api/animals/{id} : Get a specific animal by its ID.
   *
   * @param id The ID of the animal to retrieve.
   * @return ResponseEntity containing the animal DTO if found (200 OK), or 404 Not Found.
   */
  @GetMapping("/{id}")
  public ResponseEntity<AnimalDto> getAnimalById(
      @PathVariable UUID id
  ) {
    return animalManagementService.getAnimalById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  /**
   * POST /api/animals : Add a new animal.
   *
   * @param addAnimalDto The animal data from the request body.
   * @return ResponseEntity with the created animal DTO (201 Created) and Location header.
   */
  @PostMapping
  public ResponseEntity<AnimalDto> addAnimal(
      @RequestBody AddAnimalDto addAnimalDto
  ) {
    AnimalDto createdAnimal = animalManagementService.addAnimal(addAnimalDto);

    URI location = ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(createdAnimal.id())
        .toUri();

    return ResponseEntity.created(location).body(createdAnimal);
  }

  /**
   * DELETE /api/animals/{id} : Delete an animal by its ID.
   *
   * @param id The ID of the animal to delete.
   * @return ResponseEntity with no content (204 No Content).
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteAnimal(
      @PathVariable UUID id
  ) {
    animalManagementService.deleteAnimal(id);
    return ResponseEntity.noContent().build();
  }
}
