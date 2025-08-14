package manolovisoromero.person_processor.dto;


import com.fasterxml.jackson.annotation.*;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL) // Exclude null fields from JSON
@Builder
public class PersonDto implements Dto {

    private int id;
    private String name;
    private int parent1Id;
    private int parent2Id;
    private int partnerId;
    private Set<Integer> children;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    @JsonCreator
    public PersonDto() {}
}
