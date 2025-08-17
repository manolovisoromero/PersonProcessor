package manolovisoromero.person_processor.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import manolovisoromero.person_processor.dto.PersonDto;
import manolovisoromero.person_processor.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PersonMapperTest {


    private PersonMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new PersonMapper();
    }


    @Test
    void shouldMapDtoMapCorrectly() {
        PersonDto dto = PersonDto.builder()
                .id(1L)
                .name("Jan")
                .dateOfBirth(LocalDate.of(1990, 1, 1))
                .parentIds(Set.of(2L, 3L))
                .partnerId(4L)
                .childrenIds(Set.of(5L, 6L))
                .build();

        Person entity = mapper.map(dto);

        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getName(), entity.getName());
        assertEquals(dto.getDateOfBirth(), entity.getDateOfBirth());
        assertEquals(dto.getParentIds(), entity.getParentIds());
        assertEquals(dto.getPartnerId(), entity.getPartnerId());
        assertEquals(dto.getChildrenIds(), entity.getChildrenIds());
    }

    @Test
    void shouldMapEntityMapCorrectly() {
        Person person = Person.builder()
                .id(1L)
                .name("Jan")
                .dateOfBirth(LocalDate.of(1985, 5, 20))
                .parentIds(Set.of(7L))
                .partnerId(8L)
                .childrenIds(Set.of(9L, 10L))
                .build();

        PersonDto dto = mapper.mapToDto(person);

        assertEquals(person.getId(), dto.getId());
        assertEquals(person.getName(), dto.getName());
        assertEquals(person.getDateOfBirth(), dto.getDateOfBirth());
        assertEquals(person.getParentIds(), dto.getParentIds());
        assertEquals(person.getPartnerId(), dto.getPartnerId());
        assertEquals(person.getChildrenIds(), dto.getChildrenIds());
    }

    @Test
    void shouldPreserveDataInRoundTripConversion() {
        PersonDto originalDto = PersonDto.builder()
                .id(1L)
                .name("Jan")
                .dateOfBirth(LocalDate.of(2000, 2, 2))
                .parentIds(Set.of(2L, 3L))
                .partnerId(4L)
                .childrenIds(Set.of(5L))
                .build();

        Person entity = mapper.map(originalDto);
        PersonDto mappedBackDto = mapper.mapToDto(entity);

        assertEquals(originalDto, mappedBackDto);
    }

    @Test
    void shouldFailValidationWhenIdIsNull() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        PersonDto dto = PersonDto.builder()
                .name("No ID")
                .dateOfBirth(LocalDate.of(1995, 3, 3))
                .build(); // no id

        Set<ConstraintViolation<PersonDto>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("id")));
    }

    @Test
    void shouldSerializeDateOfBirthCorrectly() throws Exception {
        ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

        PersonDto dto = PersonDto.builder()
                .id(1L)
                .name("Jan")
                .dateOfBirth(LocalDate.of(2020, 12, 25))
                .build();

        String json = mapper.writeValueAsString(dto);

        assertTrue(json.contains("\"dateOfBirth\":\"2020-12-25\""));
    }
}