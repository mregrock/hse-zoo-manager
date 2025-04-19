package com.hse.zoo.manager.application.service.impl;

import com.hse.zoo.manager.application.service.AnimalTransferService;
import com.hse.zoo.manager.domain.event.AnimalMovedEvent;
import com.hse.zoo.manager.domain.model.Animal;
import com.hse.zoo.manager.domain.model.Enclosure;
import com.hse.zoo.manager.domain.repository.AnimalRepository;
import com.hse.zoo.manager.domain.repository.EnclosureRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AnimalTransferServiceImpl implements AnimalTransferService {

private final AnimalRepository animalRepository;
private final EnclosureRepository enclosureRepository;

@Override
@Transactional
public void moveAnimal(UUID animalId, UUID newEnclosureId) {
  Animal animal = findAnimalOrThrow(animalId);
  Enclosure newEnclosure = findEnclosureOrThrow(newEnclosureId);

  if (!newEnclosure.canAddAnimal()) {
    throw new IllegalStateException("Enclosure " + newEnclosureId + " is full.");
  }

  UUID oldEnclosureId = animal.getEnclosureId();

  if (oldEnclosureId != null) {
    if(oldEnclosureId.equals(newEnclosureId)) {
      return; 
    }
    Enclosure oldEnclosure = findEnclosureOrThrow(oldEnclosureId);
    oldEnclosure.removeAnimalInternal(animalId);
    enclosureRepository.save(oldEnclosure);
  }

  animal.moveToEnclosureInternal(newEnclosureId);
  newEnclosure.addAnimalInternal(animalId);

  animalRepository.save(animal);
  enclosureRepository.save(newEnclosure);

  System.out.println("Published AnimalMovedEvent for animal " + animalId);
}

@Override
@Transactional
public void removeAnimalFromEnclosure(UUID animalId) {
  Animal animal = findAnimalOrThrow(animalId);
  UUID oldEnclosureId = animal.getEnclosureId();

  if (oldEnclosureId == null) {
      return;
  }

  Enclosure oldEnclosure = findEnclosureOrThrow(oldEnclosureId);

  animal.removeFromEnclosureInternal();
  oldEnclosure.removeAnimalInternal(animalId);

  animalRepository.save(animal);
  enclosureRepository.save(oldEnclosure);

  System.out.println("Published AnimalMovedEvent for animal " + animalId + " removed from enclosure");
}

private Animal findAnimalOrThrow(UUID animalId) {
  return animalRepository.findById(animalId)
      .orElseThrow(() -> new RuntimeException("Animal not found: " + animalId));
}

private Enclosure findEnclosureOrThrow(UUID enclosureId) {
  return enclosureRepository.findById(enclosureId)
      .orElseThrow(() -> new RuntimeException("Enclosure not found: " + enclosureId));
}
}
