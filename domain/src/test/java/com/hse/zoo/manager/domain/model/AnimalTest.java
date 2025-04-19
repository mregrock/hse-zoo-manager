package com.hse.zoo.manager.domain.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AnimalTest {

    @Test
    void createNew_shouldInitializeCorrectly() {
        String species = "Lion";
        String name = "Simba";
        LocalDate dob = LocalDate.of(2005, 7, 3);
        Gender gender = Gender.MALE;
        FoodType food = FoodType.MEAT;

        Animal simba = Animal.createNew(species, name, dob, gender, food);

        assertNotNull(simba.getId());
        assertEquals(species, simba.getSpecies());
        assertEquals(name, simba.getName());
        assertEquals(dob, simba.getDateOfBirth());
        assertEquals(gender, simba.getGender());
        assertEquals(food, simba.getFavoriteFood());
        assertEquals(AnimalStatus.HEALTHY, simba.getStatus());
        assertNull(simba.getEnclosureId());
    }

    @Test
    void createNew_shouldThrowException_whenSpeciesIsEmpty() {
        String emptySpecies = "";
        String name = "EgorKulin";
        LocalDate dob = LocalDate.of(2005, 7, 3);
        Gender gender = Gender.FEMALE;
        FoodType food = FoodType.MEAT;

        assertThrows(IllegalArgumentException.class, () -> {
            Animal.createNew(emptySpecies, name, dob, gender, food);
        });
    }

    @Test
    void treat_shouldChangeStatusToUnderTreatment_whenSick() {
        Animal scar = Animal.createNew("Lion", "Lev", LocalDate.of(2003, 3, 3), Gender.MALE, FoodType.MEAT);
        scar.setStatus(AnimalStatus.SICK);

        scar.treat();

        assertEquals(AnimalStatus.UNDER_TREATMENT, scar.getStatus());
    }

     @Test
    void treat_shouldNotChangeStatus_whenHealthy() {
        Animal simba = Animal.createNew("Lion", "Simba", LocalDate.of(2001, 9, 11), Gender.MALE, FoodType.MEAT);
        assertEquals(AnimalStatus.HEALTHY, simba.getStatus());

        simba.treat();
        assertEquals(AnimalStatus.HEALTHY, simba.getStatus());
    }

    @Test
    void feed_shouldKeepStatusHealthy_whenHealthy() {
        Animal simba = Animal.createNew("Lion", "Simba", LocalDate.of(2005, 7, 3), Gender.MALE, FoodType.MEAT);
        assertEquals(AnimalStatus.HEALTHY, simba.getStatus());
        simba.feed(FoodType.MEAT);

        assertEquals(AnimalStatus.HEALTHY, simba.getStatus());
    }

    @Test
    void feed_shouldKeepStatusSick_whenSick() {
        Animal scar = Animal.createNew("Lion", "EgorKulin", LocalDate.of(2003, 3, 3), Gender.MALE, FoodType.MEAT);
        scar.setStatus(AnimalStatus.SICK);
        assertEquals(AnimalStatus.SICK, scar.getStatus());
        scar.feed(FoodType.MEAT);
        assertEquals(AnimalStatus.SICK, scar.getStatus());
    }

    @Test
    void markAsHealthy_shouldChangeStatus_whenNotHealthy() {
        Animal nala = Animal.createNew("Lion", "Nala", LocalDate.of(2004, 1, 1), Gender.FEMALE, FoodType.MEAT);
        nala.setStatus(AnimalStatus.SICK);

        nala.markAsHealthy();

        assertEquals(AnimalStatus.HEALTHY, nala.getStatus());
        nala.setStatus(AnimalStatus.UNDER_TREATMENT);
        nala.markAsHealthy();

        assertEquals(AnimalStatus.HEALTHY, nala.getStatus());
    }

     @Test
    void markAsHealthy_shouldNotChangeStatus_whenAlreadyHealthy() {
        Animal simba = Animal.createNew("Lion", "Simba", LocalDate.of(2005, 7, 3), Gender.MALE, FoodType.MEAT);
        assertEquals(AnimalStatus.HEALTHY, simba.getStatus());

        simba.markAsHealthy();

        assertEquals(AnimalStatus.HEALTHY, simba.getStatus());
    }

    @Test
    void moveToEnclosureInternal_shouldUpdateEnclosureIdAndReturnOld() {
        Animal timon = Animal.createNew("Meerkat", "Timon", LocalDate.of(2005, 7, 3), Gender.MALE, FoodType.INSECTS);
        UUID firstEnclosureId = UUID.randomUUID();
        UUID secondEnclosureId = UUID.randomUUID();
        assertNull(timon.getEnclosureId());
        UUID previousId1 = timon.moveToEnclosureInternal(firstEnclosureId);
        assertNull(previousId1);
        assertEquals(firstEnclosureId, timon.getEnclosureId());
        UUID previousId2 = timon.moveToEnclosureInternal(secondEnclosureId);

        assertEquals(firstEnclosureId, previousId2);
        assertEquals(secondEnclosureId, timon.getEnclosureId());
    }

     @Test
    void removeFromEnclosureInternal_shouldSetEnclosureIdToNull() {
        Animal pumbaa = Animal.createNew("Warthog", "Pumbaa", LocalDate.of(2005, 7, 3), Gender.MALE, FoodType.PLANTS);
        UUID enclosureId = UUID.randomUUID();
        pumbaa.moveToEnclosureInternal(enclosureId);
        assertEquals(enclosureId, pumbaa.getEnclosureId());
        pumbaa.removeFromEnclosureInternal();

        assertNull(pumbaa.getEnclosureId());
        pumbaa.removeFromEnclosureInternal();
        assertNull(pumbaa.getEnclosureId());
    }
}
