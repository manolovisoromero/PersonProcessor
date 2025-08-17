package manolovisoromero.person_processor.storage;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "person")
@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PersonEntity {

    @Id
    Long id;

    String name;

    LocalDate dateOfBirth;

    Set<Long> parentIds;

    Long partnerId;

    Set<Long> childrenIds;
}
