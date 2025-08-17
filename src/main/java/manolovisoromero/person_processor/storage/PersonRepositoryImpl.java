package manolovisoromero.person_processor.storage;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import manolovisoromero.person_processor.model.Person;
import org.springframework.beans.factory.annotation.Autowired;

public class PersonRepositoryImpl implements PersonRepositoryCustom {

    @Autowired
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public boolean saveAndDetect(PersonEntity person) {
        boolean exists = entityManager.find(PersonEntity.class, person.getId()) != null;
        entityManager.merge(person);
        return exists;
    }

}
