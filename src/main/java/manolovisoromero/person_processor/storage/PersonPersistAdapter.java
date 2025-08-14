package manolovisoromero.person_processor.storage;

import manolovisoromero.person_processor.model.Person;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class PersonPersistAdapter implements  PersistAdapter<Person>{

    private final Map<Long, Person>  personDb = new HashMap<>();

    @Override
    public void connect() {
    }

    @Override
    public void closeConnection() {
    }

    @Override
    public Person save(Person data) {
        personDb.put(data.getId(), data);
        return data;
    }

    @Override
    public Collection<Person> saveAndGetAll(Person data) {
        personDb.put(data.getId(), data);
        return personDb.values();
    }

    @Override
    public Optional<Person> getById(Long id) {
        return Optional.of(personDb.get(id));
    }


}
