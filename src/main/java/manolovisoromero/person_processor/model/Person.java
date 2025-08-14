package manolovisoromero.person_processor.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.time.LocalDate;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Builder(toBuilder = true)
@Value
public class Person extends Entity{

    Long id;
    String name;
    LocalDate dateOfBirth;
    Set<Long> parentIds;
    Long partnerId;
    Set<Long> childrenIds;
    }
