package manolovisoromero.person_processor.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PersonTest {


    @Test
    void shouldApplyDefaultValues() {
        Person person = Person.builder()
                .id(1L)
                .name("Jan")
                .dateOfBirth(LocalDate.of(1990, 1, 1))
                .build();

        assertNotNull(person.getParentIds());
        assertTrue(person.getParentIds().isEmpty());

        assertNotNull(person.getChildrenIds());
        assertTrue(person.getChildrenIds().isEmpty());
    }


    @Test
    void shouldCreateModifiedCopyWithToBuilder() {
        Person original = Person.builder()
                .id(1L)
                .name("Jan")
                .dateOfBirth(LocalDate.of(1990, 1, 1))
                .build();

        Person modified = original.toBuilder()
                .name("Kees")
                .build();

        assertEquals("Jan", original.getName());
        assertEquals("Kees", modified.getName());
    }

}