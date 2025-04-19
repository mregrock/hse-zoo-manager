package com.hse.zoo.manager.application.service.impl;

import com.hse.zoo.manager.application.dto.AddEnclosureDto;
import com.hse.zoo.manager.application.dto.EnclosureDto;
import com.hse.zoo.manager.application.service.EnclosureManagementService;
import com.hse.zoo.manager.domain.model.Enclosure;
import com.hse.zoo.manager.domain.repository.EnclosureRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EnclosureManagementServiceImpl implements EnclosureManagementService {

  private final EnclosureRepository enclosureRepository;

  @Override
  @Transactional
  public EnclosureDto addEnclosure(AddEnclosureDto addEnclosureDto) {
    Enclosure newEnclosure = Enclosure.createNew(
        addEnclosureDto.type(),
        addEnclosureDto.size(),
        addEnclosureDto.maxCapacity()
    );
    Enclosure savedEnclosure = enclosureRepository.save(newEnclosure);
    return mapToEnclosureDto(savedEnclosure);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<EnclosureDto> getEnclosureById(UUID id) {
    return enclosureRepository.findById(id).map(this::mapToEnclosureDto);
  }

  @Override
  @Transactional(readOnly = true)
  public List<EnclosureDto> getAllEnclosures() {
    return enclosureRepository.findAll().stream()
        .map(this::mapToEnclosureDto)
        .collect(Collectors.toList());
  }

  @Override
  @Transactional
  public void deleteEnclosure(UUID id) {
    Enclosure enclosure = enclosureRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Enclosure not found: " + id));

    if (!enclosure.getAnimalIds().isEmpty()) {
      throw new IllegalStateException(
          "Cannot delete enclosure " + id + " because it contains animals.");
    }
    enclosureRepository.deleteById(id);
  }

  private EnclosureDto mapToEnclosureDto(Enclosure enclosure) {
    return new EnclosureDto(
        enclosure.getId(),
        enclosure.getType(),
        enclosure.getSize(),
        enclosure.getMaxCapacity(),
        enclosure.getCurrentAnimalCount(),
        enclosure.getAnimalIds()
    );
  }
}
