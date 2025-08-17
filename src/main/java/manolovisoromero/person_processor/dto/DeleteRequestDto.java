package manolovisoromero.person_processor.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class DeleteRequestDto {

    @NotEmpty
    private List<Long> ids;
}
