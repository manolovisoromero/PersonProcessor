package manolovisoromero.person_processor.model;

import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Builder(toBuilder = true)
@Value
public class PersonImpl implements Person {

    Long id;
    String name;
    LocalDate dateOfBirth;
    Set<Long> parentIds;
    Long partnerId;
    Set<Long> childrenIds;
}
