package manolovisoromero.person_processor.model;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;
import lombok.With;
import manolovisoromero.person_processor.validation.NotSelfReferencing;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@NotSelfReferencing
@Builder(toBuilder = true)
@Data
@With
@Entity
@Table(name = "person")
public class Person {

    @Id
    Long id;

    String name;

    LocalDate dateOfBirth;

    @Builder.Default
    @ElementCollection
    Set<Long> parentIds = new HashSet<>();

    Long partnerId;

    @Builder.Default
    @ElementCollection
    Set<Long> childrenIds = new HashSet<>();
}