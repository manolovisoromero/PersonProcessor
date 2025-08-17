package manolovisoromero.person_processor.service;

import manolovisoromero.person_processor.model.Person;

public interface CheckResult {

    Person getMatchingPerson();

    String getMessage();
}
