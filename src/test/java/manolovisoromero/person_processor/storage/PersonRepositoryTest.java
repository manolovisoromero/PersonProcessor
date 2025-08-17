package manolovisoromero.person_processor.storage;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
public class PersonRepositoryTest {

    @Autowired
    private PersonRepository repository;

    @Test
    void shouldSaveAndDetectNewPerson() {
        PersonEntity person = PersonEntity.builder()
                .id(1L)
                .name("Jan")
                .dateOfBirth(LocalDate.of(1990, 1, 1))
                .partnerId(2L)
                .parentIds(Set.of(3L, 4L))
                .childrenIds(Set.of(5L, 6L))
                .build();

        boolean updated = repository.saveAndDetect(person);
        assertFalse(updated);

        Optional<PersonEntity> result = repository.findById(1L);
        assertTrue(result.isPresent());
        assertEquals("Jan", result.get().getName());
    }

    @Test
    void shouldDetectUpdateOnExistingPerson() {
        PersonEntity original = PersonEntity.builder().id(2L).name("Jan").build();
        repository.save(original);

        PersonEntity updated = PersonEntity.builder().id(2L).name("Kees").build();
        boolean wasUpdated = repository.saveAndDetect(updated);

        assertTrue(wasUpdated);

        Optional<PersonEntity> result = repository.findById(2L);
        assertTrue(result.isPresent());
        assertEquals("Kees", result.get().getName());
    }

    @Test
    void shouldSaveMultiplePersons() {
        PersonEntity p1 = PersonEntity.builder().id(10L).name("Jan").build();
        PersonEntity p2 = PersonEntity.builder().id(11L).name("Kees").build();

        repository.saveAll(List.of(p1, p2));

        List<PersonEntity> all = repository.findAll();
        assertEquals(2, all.size());
        assertTrue(all.stream().anyMatch(p -> p.getName().equals("Jan")));
        assertTrue(all.stream().anyMatch(p -> p.getName().equals("Kees")));
    }

    @Test
    void shouldReturnAllPersons() {
        repository.saveAll(List.of(
                PersonEntity.builder().id(20L).name("Jan").build(),
                PersonEntity.builder().id(21L).name("Kees").build()
        ));

        List<PersonEntity> all = repository.findAll();
        assertEquals(2, all.size());
        assertTrue(all.stream().anyMatch(p -> p.getName().equals("Jan")));
        assertTrue(all.stream().anyMatch(p -> p.getName().equals("Kees")));
    }
}
