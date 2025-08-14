package manolovisoromero.person_processor.mapper;

import manolovisoromero.person_processor.model.Person;
import manolovisoromero.person_processor.dto.PersonDto;
import org.springframework.stereotype.Component;

@Component
public class PersonMapper {

    public Person toEntity(PersonDto dto) {
        return Person.builder()
                .id(dto.getId())
                .name(dto.getName())
                .dateOfBirth(dto.getDateOfBirth())
                .parentIds(dto.getParentIds())
                .partnerId(dto.getPartnerId())
                .childrenIds(dto.getChildrenIds())
                .build();
    }

    public PersonDto toDto(Person person) {
        return PersonDto.builder()
                .id(person.getId())
                .name(person.getName())
                .dateOfBirth(person.getDateOfBirth())
                .parentIds(person.getParentIds())
                .partnerId(person.getPartnerId())
                .childrenIds(person.getChildrenIds())
                .build();

    }
}
