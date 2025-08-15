package manolovisoromero.person_processor.service;

import manolovisoromero.person_processor.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PersonProcessorTest {

    private PersonProcessor processor;

    @BeforeEach
    void setUp() {
        processor = new PersonProcessor();
    }

    @Test
    void shouldReturnSatisfiedWhenPersonMeetsCriteria() {
        Person parent = Person.builder()
                .id(1L)
                .partnerId(2L)
                .childrenIds(Set.of(10L, 11L, 12L))
                .build();

        Person child1 = Person.builder()
                .id(10L)
                .parentIds(Set.of(1L, 2L))
                .dateOfBirth(LocalDate.now().minusYears(5))
                .build();

        Person child2 = Person.builder()
                .id(11L)
                .parentIds(Set.of(1L, 2L))
                .dateOfBirth(LocalDate.now().minusYears(10))
                .build();

        Person child3 = Person.builder()
                .id(12L)
                .parentIds(Set.of(1L, 2L))
                .dateOfBirth(LocalDate.now().minusYears(8))
                .build();

        Collection<Person> persons = Set.of(parent, child1, child2, child3);

        CheckResult result = processor.executeCriteriaCheck(persons);

        assertTrue(result.satisfied());
    }

    @Test
    void shouldReturnNotSatisfiedWhenPartnerIsMissing() {
        Person parent = Person.builder()
                .id(1L)
                .partnerId(null) 
                .childrenIds(Set.of(10L, 11L, 12L))
                .build();

        Person child1 = Person.builder()
                .id(10L)
                .parentIds(Set.of(1L))
                .dateOfBirth(LocalDate.now().minusYears(5))
                .build();

        Person child2 = Person.builder()
                .id(11L)
                .parentIds(Set.of(1L))
                .dateOfBirth(LocalDate.now().minusYears(10))
                .build();

        Person child3 = Person.builder()
                .id(12L)
                .parentIds(Set.of(1L))
                .dateOfBirth(LocalDate.now().minusYears(8))
                .build();

        Collection<Person> persons = Set.of(parent, child1, child2, child3);

        CheckResult result = processor.executeCriteriaCheck(persons);

        assertFalse(result.satisfied());
    }

    @Test
    void shouldAddCurrentPersonAsParentIfMissing() {
        Person current = Person.builder()
                .id(1L)
                .childrenIds(Set.of(10L))
                .build();

        Person child = Person.builder()
                .id(10L)
                .parentIds(Set.of(2L))
                .build();

        Collection<Person> all = Set.of(current, child);

        Collection<Person> updated = processor.getListToEnforceIntegrity(current, all);

        assertEquals(1, updated.size());
        Person updatedChild = updated.iterator().next();
        assertTrue(updatedChild.getParentIds().contains(1L));
        assertTrue(updatedChild.getParentIds().contains(2L));
    }
    @Test
    void shouldNotUpdateChildIfCurrentAlreadyListedAsParent() {
        Person current = Person.builder()
                .id(1L)
                .childrenIds(Set.of(10L))
                .build();

        Person child = Person.builder()
                .id(10L)
                .parentIds(Set.of(1L, 2L))
                .build();

        Collection<Person> all = Set.of(current, child);

        Collection<Person> updated = processor.getListToEnforceIntegrity(current, all);

        assertTrue(updated.isEmpty());
    }

    @Test
    void shouldSkipUpdateIfChildHasTwoOtherParents() {
        Person current = Person.builder()
                .id(1L)
                .childrenIds(Set.of(10L))
                .build();

        Person child = Person.builder()
                .id(10L)
                .parentIds(Set.of(2L, 3L))
                .build();

        Collection<Person> all = Set.of(current, child);

        Collection<Person> updated = processor.getListToEnforceIntegrity(current, all);

        assertTrue(updated.isEmpty());
    }

    @Test
    void shouldAddCurrentAsChildToParentIfMissing() {
        Person current = Person.builder()
                .id(10L)
                .parentIds(Set.of(1L))
                .build();

        Person parent = Person.builder()
                .id(1L)
                .childrenIds(Set.of())
                .build();

        Collection<Person> all = Set.of(current, parent);

        Collection<Person> updated = processor.getListToEnforceIntegrity(current, all);

        assertEquals(1, updated.size());
        Person updatedParent = updated.iterator().next();
        assertTrue(updatedParent.getChildrenIds().contains(10L));
    }

    @Test
    void shouldReturnEmptyWhenNoIntegrityIssuesExist() {
        Person current = Person.builder()
                .id(1L)
                .childrenIds(Set.of(10L))
                .build();

        Person child = Person.builder()
                .id(10L)
                .parentIds(Set.of(1L))
                .build();

        Collection<Person> all = Set.of(current, child);

        Collection<Person> updated = processor.getListToEnforceIntegrity(current, all);

        assertTrue(updated.isEmpty());
    }
    }