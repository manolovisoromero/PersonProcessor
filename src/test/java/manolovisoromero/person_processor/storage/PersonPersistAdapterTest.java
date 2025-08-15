package manolovisoromero.person_processor.storage;

import manolovisoromero.person_processor.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class PersonPersistAdapterTest {


    private PersonPersistAdapter adapter;

    @BeforeEach
    void setUp() {
        adapter = new PersonPersistAdapter();
    }

    @Test
    void shouldSaveNewPerson() {
        Person person = Person.builder()
                .id(1L)
                .name("Jan")
                .build();

        Person result = adapter.save(person);

        assertNull(result);
        assertEquals(Optional.of(person), adapter.getById(1L));
    }

    @Test
    void shouldUpdateExistingPerson() {
        Person original = Person.builder().id(1L).name("Jan").build();
        adapter.save(original);

        Person updated = Person.builder().id(1L).name("Kees").build();
        Person result = adapter.save(updated);

        assertEquals(original, result);
        assertEquals(Optional.of(updated), adapter.getById(1L));
    }

    @Test
    void shouldReturnAllPersons() {
        Person p1 = Person.builder().id(1L).name("Jan").build();
        Person p2 = Person.builder().id(2L).name("Kees").build();

        adapter.save(p1);
        adapter.save(p2);

        Collection<Person> all = adapter.returnAll();

        assertEquals(2, all.size());
        assertTrue(all.contains(p1));
        assertTrue(all.contains(p2));
    }

    @Test
    void shouldDeletePersonById() {
        Person person = Person.builder().id(1L).name("Jan").build();
        adapter.save(person);

        adapter.deleteById(1L);

        assertTrue(adapter.getById(1L).isEmpty());
    }

    @Test
    void shouldClearDatabase() {
        adapter.save(Person.builder().id(1L).name("Alice").build());
        adapter.save(Person.builder().id(2L).name("Bob").build());

        adapter.clear();

        assertTrue(adapter.returnAll().isEmpty());
    }

    @Test
    void shouldReturnEmptyOptionalIfPersonNotFound() {
        Optional<Person> result = adapter.getById(999L);
        assertTrue(result.isEmpty());
    }



}