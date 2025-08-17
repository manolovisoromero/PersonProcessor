package manolovisoromero.person_processor.service;

import lombok.Builder;
import manolovisoromero.person_processor.model.Person;

import java.util.Optional;


@Builder
public record FailureResult(String message) implements CheckResult {
    @Override
    public Optional<Person> matchingPerson() {
        return Optional.empty();
    }
}
