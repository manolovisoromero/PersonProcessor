package manolovisoromero.person_processor.model;

import java.time.LocalDate;
import java.util.Set;

public interface Person extends Entity{

    Long getId();

    String getName();

    LocalDate getDateOfBirth();

    Long getParent1Id();

    Long getParent2Id();

    Long getPartnerId();

    Set<Long> getChildren();
    }
