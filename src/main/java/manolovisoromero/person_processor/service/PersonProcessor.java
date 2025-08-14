package manolovisoromero.person_processor.service;


import manolovisoromero.person_processor.dto.PersonDto;
import manolovisoromero.person_processor.model.Person;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Year;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class PersonProcessor implements Processor<PersonDto> {

    private PersonProcessor(){    }


    @Override
    public CheckResult executeCriteriaCheck(Collection<Person> persons) {
        return CheckResultImpl.builder()
                .satisfied(personMeetsCriteria(persons))
                .build();
    }



    private boolean personMeetsCriteria(Collection<Person> persons){
        Map<Long, Person> personMap = persons.stream().collect(Collectors.toMap(Person::getId, Function.identity()));

        for(Person person: persons){
            Long partnerId = person.getPartnerId();
            if(hasPartner(person)) continue;
            List<Person> children = person.getChildren().stream()
                    .map(personMap::get)
                    .filter(Objects::nonNull)
                    .toList();
            if (!hasUnderageChild(children))  continue;
            if (!exactlyThreeChildrenOfSameParents(children, partnerId)) continue;
            return true;
        }
        return false;
    }

    private boolean hasPartner(Person person){
        return person.getPartnerId() == null;
    }

    private boolean exactlyThreeChildrenOfSameParents(List<Person> children, Long partnerId){
        return childrenOfSamePartner(children, partnerId) && exactlyThreeChildren(children);
    }

    private boolean childrenOfSamePartner(List<Person> children, Long partnerId) {
        return children.stream()
                .anyMatch(child ->
                        (partnerId.equals(child.getParent2Id()) ||
                                (partnerId.equals(child.getParent1Id()))
                        )
                );
    }

    private boolean exactlyThreeChildren(List<Person> children) {
        return children.size() != 3;
    }

    private boolean hasUnderageChild(List<Person> children){
        return children.stream().anyMatch(child -> child.getDateOfBirth().isBefore(LocalDate.now().minusYears(18)));
    }
}
