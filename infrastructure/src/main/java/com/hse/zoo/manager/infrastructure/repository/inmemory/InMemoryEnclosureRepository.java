package com.hse.zoo.manager.infrastructure.repository.inmemory;

import com.hse.zoo.manager.domain.model.Enclosure;
import com.hse.zoo.manager.domain.repository.EnclosureRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryEnclosureRepository implements EnclosureRepository {

  private final Map<UUID, Enclosure> enclosures = new ConcurrentHashMap<>();

  @Override
  public Optional<Enclosure> findById(UUID id) {
    return Optional.ofNullable(enclosures.get(id));
  }

  @Override
  public List<Enclosure> findAll() {
    return new ArrayList<>(enclosures.values());
  }

  @Override
  public Enclosure save(Enclosure enclosure) {
    enclosures.put(enclosure.getId(), enclosure);
    return enclosure;
  }

  @Override
  public void deleteById(UUID id) {
    enclosures.remove(id);
  }
} 