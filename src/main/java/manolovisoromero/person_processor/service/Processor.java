package manolovisoromero.person_processor.service;


import manolovisoromero.person_processor.dto.Dto;
import manolovisoromero.person_processor.model.Person;

import java.util.Collection;

public interface Processor <T extends Dto> {

    CheckResult executeCriteriaCheck(Collection<Person> dto);
}
