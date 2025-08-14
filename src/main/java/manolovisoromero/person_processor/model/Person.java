package manolovisoromero.person_processor.model;

import java.time.LocalDate;
import java.util.Set;

public interface Person extends Entity{

    Long getId();

    String getName();

    LocalDate getDateOfBirth();

    Set<Long> getParentIds();

    Long getPartnerId();

    Set<Long> getChildrenIds();
    }
