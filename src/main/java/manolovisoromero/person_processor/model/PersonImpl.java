package manolovisoromero.person_processor.model;

import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Builder
public record PersonImpl(int id, String name, LocalDate dateOfBirth, int parent1Id, int parent2Id, int partnerId,
                         Set<Integer> children) implements Person {

}
