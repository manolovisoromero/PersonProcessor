package manolovisoromero.person_processor.dto;


import com.fasterxml.jackson.annotation.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@EqualsAndHashCode
public class PersonDto implements Dto {

    @NotNull
    private Long id;
    private String name;
    @Builder.Default
    Set<Long> parentIds = new HashSet<>();
    Long partnerId;
    @Builder.Default
    Set<Long> childrenIds = new HashSet<>();
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;
}
