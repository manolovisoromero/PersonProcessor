package manolovisoromero.person_processor.service;

import lombok.RequiredArgsConstructor;
import manolovisoromero.person_processor.dto.PersonDto;
import manolovisoromero.person_processor.mapper.PersonMapper;
import manolovisoromero.person_processor.model.Person;
import manolovisoromero.person_processor.storage.PersistAdapter;
import manolovisoromero.person_processor.storage.PersonPersistAdapter;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final Processor<PersonDto> processor;
    private final PersistAdapter<Person> persistAdapter;
    private final PersonMapper mapper = new PersonMapper();


    public CheckResult processPerson(PersonDto dto){
        final Person person = mapper.toEntity(dto);
        final var persons = persistAdapter.saveAndGetAll(person);
        return processor.executeCriteriaCheck(persons);
    }
}
