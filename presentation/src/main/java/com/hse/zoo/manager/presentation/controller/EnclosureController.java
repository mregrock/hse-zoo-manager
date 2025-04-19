package com.hse.zoo.manager.presentation.controller;

import com.hse.zoo.manager.application.dto.AddEnclosureDto;
import com.hse.zoo.manager.application.dto.EnclosureDto;
import com.hse.zoo.manager.application.service.EnclosureManagementService;
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
 * REST controller for managing enclosures.
 */
@RestController
@RequestMapping("/api/enclosures")
@RequiredArgsConstructor
public class EnclosureController {

  private final EnclosureManagementService enclosureManagementService;

  /**
   * GET /api/enclosures : Get all enclosures.
   */
  @GetMapping
  public List<EnclosureDto> getAllEnclosures() {
    return enclosureManagementService.getAllEnclosures();
  }

  /**
   * GET /api/enclosures/{id} : Get enclosure by ID.
   */
  @GetMapping("/{id}")
  public ResponseEntity<EnclosureDto> getEnclosureById(@PathVariable UUID id) {
    return enclosureManagementService.getEnclosureById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  /**
   * POST /api/enclosures : Add a new enclosure.
   */
  @PostMapping
  public ResponseEntity<EnclosureDto> addEnclosure(@RequestBody AddEnclosureDto addEnclosureDto) {
    EnclosureDto createdEnclosure = enclosureManagementService.addEnclosure(addEnclosureDto);
    URI location = ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(createdEnclosure.id())
        .toUri();
    return ResponseEntity.created(location).body(createdEnclosure);
  }

  /**
   * DELETE /api/enclosures/{id} : Delete enclosure by ID.
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteEnclosure(@PathVariable UUID id) {
    try {
      enclosureManagementService.deleteEnclosure(id);
      return ResponseEntity.noContent().build();
    } catch (IllegalStateException e) {
      return ResponseEntity.badRequest().body(null);
    } catch (RuntimeException e) {
      return ResponseEntity.notFound().build();
    }
  }
}
