package manolovisoromero.person_processor.model;

import lombok.*;
import manolovisoromero.person_processor.validation.NotSelfReferencing;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@NotSelfReferencing
@Builder(toBuilder = true)
@Data
@With
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Person {

    Long id;

    String name;

    LocalDate dateOfBirth;

    @Builder.Default
    Set<Long> parentIds = new HashSet<>();

    Long partnerId;

    @Builder.Default
    Set<Long> childrenIds = new HashSet<>();
}