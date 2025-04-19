package com.hse.zoo.manager.infrastructure.repository.inmemory;

import com.hse.zoo.manager.domain.model.Animal;
import com.hse.zoo.manager.domain.repository.AnimalRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryAnimalRepository implements AnimalRepository {

  private final Map<UUID, Animal> animals = new ConcurrentHashMap<>();

  @Override
  public Optional<Animal> findById(UUID id) {
    return Optional.ofNullable(animals.get(id));
  }

  @Override
  public List<Animal> findAll() {
    return new ArrayList<>(animals.values());
  }

  @Override
  public Animal save(Animal animal) {
    animals.put(animal.getId(), animal);
    return animal;
  }

  @Override
  public void deleteById(UUID id) {
    animals.remove(id);
  }
} 