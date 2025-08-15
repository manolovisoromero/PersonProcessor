package manolovisoromero.person_processor.storage;

import manolovisoromero.person_processor.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Repository
public class PersonPersistAdapter implements  PersistAdapter<Person>{

    private final Map<Long, Person>  personDb = new HashMap<>();
    private static final Logger LOGGER = LoggerFactory.getLogger(PersonPersistAdapter.class);

    @Override
    public void connect() {
    }

    @Override
    public void closeConnection() {
    }

    @Override
    public Person save(Person data) {
        var result = personDb.put(data.getId(), data);
        if(result == null){
            LOGGER.atInfo().setMessage("Person {} saved to db").addArgument(data.getId()).log();
        }else{
            LOGGER.atInfo().setMessage("Person {} updated in db").addArgument(data.getId()).log();
        }
        return result;
    }

    @Override
    public Collection<Person> returnAll(Person data) {
        LOGGER.atInfo().setMessage("Persons: [{} queried").addArgument(personDb.values().stream().map(Person::getId).collect(toList())).log();
        return personDb.values();
    }

    @Override
    public Optional<Person> getById(Long id) {
        return Optional.of(personDb.get(id));
    }

    @Override
    public void deleteById(Long id) {
        LOGGER.atInfo().setMessage("Person {} deleted from db").addArgument(id).log();
        personDb.remove(id);
    }

    @Override
    public void clear() {
        personDb.clear();
        LOGGER.atInfo().setMessage("Db cleared").log();
    }
}
