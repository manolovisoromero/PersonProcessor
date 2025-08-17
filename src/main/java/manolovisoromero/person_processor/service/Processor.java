package manolovisoromero.person_processor.service;


import manolovisoromero.person_processor.model.Person;

import java.util.Collection;

public interface Processor {

    CheckResult executeCriteriaCheck(Collection<Person> entities);

    Collection<Person> getListToEnforceIntegrity(Person current, Collection<Person> all);
}
