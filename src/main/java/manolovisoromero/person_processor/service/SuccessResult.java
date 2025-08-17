package manolovisoromero.person_processor.service;


import lombok.Builder;
import manolovisoromero.person_processor.model.Person;

import java.util.Optional;

@Builder
public record SuccessResult(Optional<Person> matchingPerson, String message) implements CheckResult {

}
