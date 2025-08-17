package manolovisoromero.person_processor.model;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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