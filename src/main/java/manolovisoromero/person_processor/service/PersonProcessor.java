package manolovisoromero.person_processor.service;


import manolovisoromero.person_processor.dto.PersonDto;
import manolovisoromero.person_processor.model.Person;
import manolovisoromero.person_processor.model.PersonImpl;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;
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

    public List<Person> getPersonsToBeUpdated(PersonImpl person, List<PersonImpl> allPeople) {
        List<Person> toBeUpdated = new ArrayList<>();
        for (PersonImpl child : allPeople) {
            if (child.getId().equals(person.getId())) continue;

            if (person.getChildrenIds().contains(child.getId())) {
                Set<Long> parentIds = new LinkedHashSet<>(child.getParentIds());

                parentIds.add(person.getId());

                while (parentIds.size() > 2) {
                    Iterator<Long> it = parentIds.iterator();
                    Long removed = it.next();
                    it.remove();
                    System.out.println("Removed parent " + removed + " to add " + person.getId() +
                            " for child " + child.getId());
                }

                child.toBuilder().parentIds(parentIds);
                toBeUpdated.add(child);
            }
        }
        return toBeUpdated;
    }


    private boolean personMeetsCriteria(Collection<Person> persons){
        Map<Long, Person> personMap = persons.stream().collect(Collectors.toMap(Person::getId, Function.identity()));

        for(Person person: persons){
            Long partnerId = person.getPartnerId();
            if(hasPartner(person)) continue;
            List<Person> children = person.getChildrenIds().stream()
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
                .anyMatch(child -> child.getParentIds().contains(partnerId)
                );
    }

    private boolean exactlyThreeChildren(List<Person> children) {
        return children.size() != 3;
    }

    private boolean hasUnderageChild(List<Person> children){
        return children.stream().anyMatch(child -> child.getDateOfBirth().isBefore(LocalDate.now().minusYears(18)));
    }
}
