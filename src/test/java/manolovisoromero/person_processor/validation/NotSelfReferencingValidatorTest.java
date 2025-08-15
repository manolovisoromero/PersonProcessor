package manolovisoromero.person_processor.validation;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;
import manolovisoromero.person_processor.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.validation.Validator;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class NotSelfReferencingValidatorTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void shouldFailValidationWhenSelfReferenced() {
        Long id = 1L;
        Person person = Person.builder()
                .id(id)
                .name("Jan")
                .dateOfBirth(LocalDate.of(1990, 1, 1))
                .parentIds(Set.of(id))
                .build();

        Set<ConstraintViolation<Person>> violations = validator.validate(person);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("Person cannot be their own parent or child")));
    }

    @Test
    void shouldPassValidationWhenNoSelfReference() {
        Person person = Person.builder()
                .id(1L)
                .name("Kees")
                .dateOfBirth(LocalDate.of(1990, 1, 1))
                .parentIds(Set.of(2L))
                .build();

        Set<ConstraintViolation<Person>> violations = validator.validate(person);

        assertTrue(violations.isEmpty());
    }
}