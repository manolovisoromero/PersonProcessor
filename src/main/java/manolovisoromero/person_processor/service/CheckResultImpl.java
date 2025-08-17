package manolovisoromero.person_processor.service;


import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.Data;
import manolovisoromero.person_processor.model.Person;

@Builder
@Data
public class CheckResultImpl implements CheckResult {

    @Nullable
    Person matchingPerson;

    String message;
}
