package manolovisoromero.person_processor.mapper;

import manolovisoromero.person_processor.model.Person;
import manolovisoromero.person_processor.model.PersonImpl;
import manolovisoromero.person_processor.dto.PersonDto;
import org.springframework.stereotype.Component;

@Component
public class PersonMapper {

    public Person toEntity(PersonDto dto) {
        return PersonImpl.builder()
                .id(dto.getId())
                .name(dto.getName())
                .dateOfBirth(dto.getDateOfBirth())
                .parent1Id(dto.getParent1Id())
                .parent2Id(dto.getParent2Id())
                .partnerId(dto.getPartnerId())
                .children(dto.getChildren())
                .build();
    }

    public PersonDto toDto(Person person) {
        return PersonDto.builder()
                .id(person.getId())
                .name(person.getName())
                .dateOfBirth(person.getDateOfBirth())
                .parent1Id(person.getParent1Id())
                .parent2Id(person.getParent2Id())
                .partnerId(person.getPartnerId())
                .children(person.getChildren())
                .build();

    }
}
