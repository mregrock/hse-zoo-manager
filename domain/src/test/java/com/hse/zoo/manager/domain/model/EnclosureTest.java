package com.hse.zoo.manager.domain.model;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class EnclosureTest {

    @Test
    void createNew_shouldInitializeFieldsCorrectly() {
        double size = 50.0;
        int maxCapacity = 5;
        EnclosureType type = EnclosureType.PREDATOR;

        Enclosure enclosure = Enclosure.createNew(type, size, maxCapacity);

        assertNotNull(enclosure.getId(), "ID should be generated");
        assertEquals(size, enclosure.getSize());
        assertEquals(maxCapacity, enclosure.getMaxCapacity());
        assertEquals(type, enclosure.getType());
        assertTrue(enclosure.getAnimalIds().isEmpty(), "Newly created enclosure should have no animals");
        assertEquals(0, enclosure.getCurrentAnimalCount(), "Current animal count should be 0");
    }

    @Test
    void createNew_shouldThrowException_forInvalidArgs() {
        assertThrows(IllegalArgumentException.class, () -> Enclosure.createNew(EnclosureType.AVIARY, -10.0, 5));
        assertThrows(IllegalArgumentException.class, () -> Enclosure.createNew(EnclosureType.AVIARY, 10.0, 0));
        assertThrows(IllegalArgumentException.class, () -> Enclosure.createNew(EnclosureType.AVIARY, 10.0, -5));
    }

    @Test
    void addAnimalInternal_shouldAddAnimalId_whenNotFull() {
        Enclosure enclosure = Enclosure.createNew(EnclosureType.AVIARY, 100.0, 2);
        UUID animalId = UUID.randomUUID();

        assertDoesNotThrow(() -> enclosure.addAnimalInternal(animalId));

        assertTrue(enclosure.getAnimalIds().contains(animalId), "Animal ID should be present after adding");
        assertEquals(1, enclosure.getAnimalIds().size(), "Animal count should be 1");
        assertEquals(1, enclosure.getCurrentAnimalCount(), "Current animal count should be 1");
    }

    @Test
    void addAnimalInternal_shouldThrowException_whenFull() {
        Enclosure enclosure = Enclosure.createNew(EnclosureType.AQUARIUM, 50.0, 1);
        UUID animalId1 = UUID.randomUUID();
        UUID animalId2 = UUID.randomUUID();

        enclosure.addAnimalInternal(animalId1);

        assertThrows(IllegalStateException.class, () -> enclosure.addAnimalInternal(animalId2));

        assertFalse(enclosure.getAnimalIds().contains(animalId2), "Second animal ID should not be present");
        assertEquals(1, enclosure.getAnimalIds().size(), "Animal count should remain 1");
    }

    @Test
    void addAnimalInternal_shouldThrowException_whenAnimalAlreadyPresent() {
        Enclosure enclosure = Enclosure.createNew(EnclosureType.AQUARIUM, 50.0, 2);
        UUID animalId1 = UUID.randomUUID();
        enclosure.addAnimalInternal(animalId1);

        assertThrows(IllegalStateException.class, () -> enclosure.addAnimalInternal(animalId1));
        assertEquals(1, enclosure.getAnimalIds().size(), "Animal count should remain 1");
    }

    @Test
    void removeAnimalInternal_shouldRemoveAnimalId_whenPresent() {
        Enclosure enclosure = Enclosure.createNew(EnclosureType.GENERAL, 20.0, 2);
        UUID animalId = UUID.randomUUID();
        enclosure.addAnimalInternal(animalId);

        assertDoesNotThrow(() -> enclosure.removeAnimalInternal(animalId));

        assertFalse(enclosure.getAnimalIds().contains(animalId), "Animal ID should not be present after removal");
        assertTrue(enclosure.getAnimalIds().isEmpty(), "Animal list should be empty");
        assertEquals(0, enclosure.getCurrentAnimalCount(), "Current animal count should be 0");
    }

    @Test
    void removeAnimalInternal_shouldThrowException_whenNotPresent() {
        Enclosure enclosure = Enclosure.createNew(EnclosureType.HERBIVORE, 30.0, 2);
        UUID animalIdPresent = UUID.randomUUID();
        UUID animalIdNotPresent = UUID.randomUUID();
        enclosure.addAnimalInternal(animalIdPresent);

        assertThrows(IllegalStateException.class, () -> enclosure.removeAnimalInternal(animalIdNotPresent));

        assertTrue(enclosure.getAnimalIds().contains(animalIdPresent), "Present animal ID should still be there");
        assertEquals(1, enclosure.getAnimalIds().size(), "Animal count should remain 1");
    }

    @Test
    void canAddAnimal_shouldReturnFalse_whenCapacityReached() {
        Enclosure enclosure = Enclosure.createNew(EnclosureType.GENERAL, 5.0, 1);
        enclosure.addAnimalInternal(UUID.randomUUID());
        assertFalse(enclosure.canAddAnimal(), "canAddAnimal should return false when capacity is reached");
    }

    @Test
    void canAddAnimal_shouldReturnTrue_whenCapacityNotReached() {
        Enclosure enclosure = Enclosure.createNew(EnclosureType.AVIARY, 150.0, 2);
        enclosure.addAnimalInternal(UUID.randomUUID());
        assertTrue(enclosure.canAddAnimal(), "canAddAnimal should return true when capacity is not reached");
    }
}
