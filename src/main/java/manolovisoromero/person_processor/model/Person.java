package manolovisoromero.person_processor.model;

import java.time.LocalDate;
import java.util.Set;

public interface Person extends Entity{

    int id();

    String name();

    LocalDate dateOfBirth();

    int parent1Id();

    int parent2Id();

    int partnerId();

    Set<Integer> children();
    }
