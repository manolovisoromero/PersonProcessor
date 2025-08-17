package manolovisoromero.person_processor.mapper;

import manolovisoromero.person_processor.model.Person;
import manolovisoromero.person_processor.dto.PersonDto;
import manolovisoromero.person_processor.storage.PersonEntity;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class PersonMapper {

    public Person map(PersonDto dto) {
        return Person.builder()
                .id(dto.getId())
                .name(dto.getName())
                .dateOfBirth(dto.getDateOfBirth())
                .parentIds(dto.getParentIds())
                .partnerId(dto.getPartnerId())
                .childrenIds(dto.getChildrenIds())
                .build();
    }

    public PersonEntity map(Person domain) {
        return PersonEntity.builder()
                .id(domain.getId())
                .name(domain.getName())
                .dateOfBirth(domain.getDateOfBirth())
                .parentIds(new HashSet<>(domain.getParentIds()))
                .partnerId(domain.getPartnerId())
                .childrenIds(new HashSet<>(domain.getChildrenIds()))
                .build();
    }

    public Person map(PersonEntity entity) {
        return new Person(
                entity.getId(),
                entity.getName(),
                entity.getDateOfBirth(),
                entity.getParentIds() != null ? Set.copyOf(entity.getParentIds()) : Set.of(),
                entity.getPartnerId(),
                entity.getChildrenIds() != null ? Set.copyOf(entity.getChildrenIds()) : Set.of()
        );
    }

    public PersonDto mapToDto(Person domain) {
        return PersonDto.builder()
                .id(domain.getId())
                .name(domain.getName())
                .dateOfBirth(domain.getDateOfBirth())
                .parentIds(domain.getParentIds())
                .partnerId(domain.getPartnerId())
                .childrenIds(domain.getChildrenIds())
                .build();
    }

}
