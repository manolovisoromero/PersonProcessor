package manolovisoromero.person_processor.service;


import lombok.Builder;

@Builder
public record CheckResultImpl(boolean satisfied) implements CheckResult {
}
