package manolovisoromero.person_processor.model;

import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Builder
public record PersonImpl(Long getId, String getName, LocalDate getDateOfBirth, Long getParent1Id, Long getParent2Id, Long getPartnerId,
                         Set<Long> getChildren) implements Person {

}
