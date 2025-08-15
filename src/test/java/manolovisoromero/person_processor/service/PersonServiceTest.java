package manolovisoromero.person_processor.service;

import manolovisoromero.person_processor.dto.PersonDto;
import manolovisoromero.person_processor.model.Person;
import manolovisoromero.person_processor.storage.PersistAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PersonServiceTest {

    @Autowired
    private PersonService personService;

    @Autowired
    private PersistAdapter<Person> persistAdapter;

    @BeforeEach
    void resetStore() {
        persistAdapter.clear();
        persistAdapter.connect();
    }

    @Test
    void shouldProcessPersonAndUpdateStoreAndCriteriaNotMet() {
        PersonDto dto =  PersonDto.builder()
                .id(1L)
                .name("Jan")
                .dateOfBirth(LocalDate.of(2000, 2, 2))
                .childrenIds(Set.of(2L, 3L))
                .partnerId(4L)
                .parentIds(Set.of(5L))
                .build();

        CheckResult result = personService.processPerson(dto);

        assertFalse(result.satisfied());

        var stored = persistAdapter.returnAll();
        assertEquals(1, stored.size());
        assertEquals("Jan", stored.stream().toList().getFirst().getName());
    }
    

    @Test
    void shouldProcessPersonAndUpdateStoreAndMeetCriteria() {
        PersonDto parent1 = PersonDto.builder()
                .id(5L)
                .name("Jan")
                .partnerId(4L)
                .dateOfBirth(LocalDate.of(1970, 1, 1))
                .childrenIds(Set.of(1L, 2L, 3L))
                .build();


        personService.processPerson(parent1);

        PersonDto partner = PersonDto.builder()
                .id(4L)
                .name("Willem")
                .partnerId(5L)
                .dateOfBirth(LocalDate.of(1980, 3, 3))
                .childrenIds(Set.of(1L, 2L, 3L))
                .build();

        personService.processPerson(partner);

        PersonDto child1 = PersonDto.builder()
                .id(1L)
                .name("Arjan")
                .dateOfBirth(LocalDate.of(2000, 2, 2))
                .parentIds(Set.of(5L, 4L))
                .partnerId(4L)
                .childrenIds(Set.of(2L, 3L))
                .build();

        PersonDto child2 = PersonDto.builder()
                .id(2L)
                .name("Eric")
                .dateOfBirth(LocalDate.of(2020, 5, 5))
                .parentIds(Set.of(5L, 4L))
                .build();

        PersonDto child3 = PersonDto.builder()
                .id(3L)
                .name("Jeroen")
                .dateOfBirth(LocalDate.of(2022, 6, 6))
                .parentIds(Set.of(5L, 4L))
                .build();

        personService.processPerson(child2);
        personService.processPerson(child3);

        CheckResult result = personService.processPerson(child1);

        assertTrue(result.satisfied());

        Collection<Person> stored = persistAdapter.returnAll();
        assertEquals(5, stored.size());
    }
}
