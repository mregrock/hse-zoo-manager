package com.hse.zoo.manager.presentation.controller;

import com.hse.zoo.manager.application.dto.MoveToEnclosureRequestDto;
import com.hse.zoo.manager.application.service.AnimalTransferService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller specifically for handling animal transfer operations.
 * Uses endpoints under the /api/animals path.
 */
@RestController
@RequestMapping("/api/animals")
@RequiredArgsConstructor
public class AnimalTransferController {

  private final AnimalTransferService animalTransferService;

  /**
   * POST /api/animals/{animalId}/move-to-enclosure : Moves an animal to a specific enclosure.
   */
  @PostMapping("/{animalId}/move-to-enclosure")
  public ResponseEntity<Void> moveAnimalToEnclosure(
      @PathVariable UUID animalId,
      @RequestBody MoveToEnclosureRequestDto requestDto) {
    try {
      animalTransferService.moveAnimal(animalId, requestDto.enclosureId());
      return ResponseEntity.ok().build();
    } catch (IllegalStateException e) {
      return ResponseEntity.badRequest().body(null);
    } catch (RuntimeException e) {
      return ResponseEntity.notFound().build();
    }
  }

  /**
   * POST /api/animals/{animalId}/remove-from-enclosure : Removes an animal from its current enclosure.
   */
  @PostMapping("/{animalId}/remove-from-enclosure")
  public ResponseEntity<Void> removeAnimalFromEnclosure(@PathVariable UUID animalId) {
     try {
      animalTransferService.removeAnimalFromEnclosure(animalId);
      return ResponseEntity.ok().build();
     } catch (RuntimeException e) {
      return ResponseEntity.notFound().build();
     }
  }
}
