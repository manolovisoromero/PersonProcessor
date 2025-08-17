package manolovisoromero.person_processor.storage;

import manolovisoromero.person_processor.model.Person;

public interface PersonRepositoryCustom {
    boolean saveAndDetect(PersonEntity person);

}
