package manolovisoromero.person_processor.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import manolovisoromero.person_processor.validation.NotSelfReferencing;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@NotSelfReferencing
@EqualsAndHashCode(callSuper = true)
@Builder(toBuilder = true)
@Value
public class Person extends Entity{

    Long id;
    String name;
    LocalDate dateOfBirth;
    @Builder.Default
    Set<Long> parentIds = new HashSet<>();
    Long partnerId;
    @Builder.Default
    Set<Long> childrenIds = new HashSet<>();
    }
