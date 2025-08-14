package manolovisoromero.person_processor.dto;


import com.fasterxml.jackson.annotation.*;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class PersonDto implements Dto {

    private Long id;
    private String name;
    private Set<Long> parentIds;
    private Long partnerId;
    private Set<Long> childrenIds;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;
}
