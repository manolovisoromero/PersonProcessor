package manolovisoromero.person_processor.service;

import lombok.RequiredArgsConstructor;
import manolovisoromero.person_processor.dto.PersonDto;
import manolovisoromero.person_processor.mapper.PersonMapper;
import manolovisoromero.person_processor.model.Person;
import manolovisoromero.person_processor.storage.PersonEntity;
import manolovisoromero.person_processor.storage.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final Processor processor;
    private final PersonRepository repository;
    private final PersonMapper mapper = new PersonMapper();
    private static final Logger LOGGER = LoggerFactory.getLogger(PersonService.class);


    public CheckResult processPerson(PersonDto dto) {
        final Person newPerson = mapper.map(dto);
        save(mapper.map(newPerson));
        final List<Person> persons = getAll().stream()
                .map(mapper::map)
                .toList();
        final Collection<Person> personsToBeUpdated = processor.getListToEnforceIntegrity(newPerson, persons);
        saveAll(personsToBeUpdated.stream().map(mapper::map).toList());
        final List<Person> updatedPersons = getAll().stream().map(mapper::map).toList();
        return processor.executeCriteriaCheck(updatedPersons);
    }


    public void save(PersonEntity person) {
        boolean updated = repository.saveAndDetect(person);
        if (updated) {
            LOGGER.atInfo().setMessage("Person {} updated in db").addArgument(person.getId()).log();
        }
        LOGGER.atInfo().setMessage("Person {} saved to db").addArgument(person.getId()).log();
    }

    public void saveAll(Collection<PersonEntity> persons) {
       repository.saveAll(persons);
        LOGGER.atInfo().setMessage("Persons: {} saved").addArgument(persons.stream().map(PersonEntity::getId).collect(toList())).log();

    }


    public List<PersonEntity> getAll() {
        final var all = repository.findAll();
        LOGGER.atInfo().setMessage("Persons: {} queried").addArgument(all.stream().map(PersonEntity::getId).collect(toList())).log();
        return repository.findAll();
    }

    public void delete(Long id) {
        LOGGER.atInfo().setMessage("Person with id {} deleted").addArgument(id).log();
        repository.deleteById(id);
    }

}
