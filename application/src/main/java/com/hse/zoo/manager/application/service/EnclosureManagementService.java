package com.hse.zoo.manager.application.service;

import com.hse.zoo.manager.application.dto.AddEnclosureDto;
import com.hse.zoo.manager.application.dto.EnclosureDto;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Application service interface for managing Enclosure entities.
 */
public interface EnclosureManagementService {

  /**
   * Adds a new enclosure to the system.
   *
   * @param addEnclosureDto DTO containing the details of the enclosure to add.
   * @return The DTO of the created Enclosure.
   */
  EnclosureDto addEnclosure(AddEnclosureDto addEnclosureDto);

  /**
   * Retrieves an enclosure by its ID.
   *
   * @param id The ID of the enclosure.
   * @return An Optional containing the Enclosure DTO if found, otherwise empty.
   */
  Optional<EnclosureDto> getEnclosureById(UUID id);

  /**
   * Retrieves all enclosures.
   *
   * @return A list of Enclosure DTOs.
   */
  List<EnclosureDto> getAllEnclosures();

  /**
   * Deletes an enclosure by its ID.
   * Note: Consider implications if the enclosure contains animals.
   *       This might require moving animals first or preventing deletion.
   *
   * @param id The ID of the enclosure to delete.
   */
  void deleteEnclosure(UUID id);

} 