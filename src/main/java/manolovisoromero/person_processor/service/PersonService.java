package manolovisoromero.person_processor.service;

import lombok.RequiredArgsConstructor;
import manolovisoromero.person_processor.dto.PersonDto;
import manolovisoromero.person_processor.mapper.PersonMapper;
import manolovisoromero.person_processor.model.Person;
import manolovisoromero.person_processor.storage.PersistAdapter;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final Processor<Person> processor;
    private final PersistAdapter<Person> persistAdapter;
    private final PersonMapper mapper = new PersonMapper();


    public CheckResult processPerson(PersonDto dto){
        persistAdapter.connect();
        //Preferably use a real DB and batching/connection pooling in a proper solution
        final Person person = mapper.toEntity(dto);
        persistAdapter.save(person);
        final var persons = persistAdapter.returnAll();
        final var personsToBeUpdated = processor.getListToEnforceIntegrity(person, persons);
        for(Person personToBeUpdated: personsToBeUpdated){
            persistAdapter.save(personToBeUpdated);
        }
        return processor.executeCriteriaCheck(persons);

    }
}
