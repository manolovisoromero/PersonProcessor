package manolovisoromero.person_processor.service;


import manolovisoromero.person_processor.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class PersonProcessor implements Processor<Person> {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonProcessor.class);

    public CheckResult executeCriteriaCheck(Collection<Person> entities) {
        return CheckResultImpl.builder()
                .satisfied(criteriaAreMet(entities))
                .build();
    }


    public Collection<Person> getListToEnforceIntegrity(Person current, Collection<Person> all) {
        List<Person> toBeUpdated = new ArrayList<>();
        Set<Person> parents = all.stream().filter(person -> person.getChildrenIds().contains(current.getId()) | current.getParentIds().contains(person.getId())).collect(Collectors.toSet());
        Set<Person> children = all.stream().filter(person -> person.getParentIds().contains(current.getId()) | current.getChildrenIds().contains(person.getId())).collect(Collectors.toSet());

        for (Person child : children) {
            if (child.getParentIds().contains(current.getId())) continue;
            if (child.getParentIds().size() >= 2) {
                LOGGER.atInfo().setMessage("Person {} already has two parents, can't remove any parent.").addArgument(child.getId()).log();
                continue;
            }
            Set<Long> parentIds = new LinkedHashSet<>(child.getParentIds());

            parentIds.add(current.getId());

            toBeUpdated.add(child
                    .toBuilder()
                    .parentIds(parentIds)
                    .build());
            LOGGER.atInfo().setMessage("Added parent {} to person {}").addArgument(current.getId()).addArgument(child.getId()).log();

        }
        for (Person parent: parents){
            Set<Long> childrenIds = new LinkedHashSet<>(parent.getChildrenIds());
            childrenIds.add(current.getId());

            toBeUpdated.add(parent
                    .toBuilder()
                    .childrenIds(childrenIds)
                    .build());
            LOGGER.atInfo().setMessage("Added child {} to person {}").addArgument(current.getId()).addArgument(parent.getId()).log();
        }
        return toBeUpdated;
    }



    private boolean criteriaAreMet(Collection<Person> persons) {
        Map<Long, Person> personMap = persons.stream().collect(Collectors.toMap(Person::getId, Function.identity()));

        for (Person person : persons) {
            Long partnerId = person.getPartnerId();
            if (!hasPartner(person)) continue;
            List<Person> children = person.getChildrenIds().stream()
                    .map(personMap::get)
                    .filter(Objects::nonNull)
                    .toList();
            if (!hasUnderageChild(children)) continue;
            if (!exactlyThreeChildrenOfSameParents(children, partnerId)) continue;
            return true;
        }
        return false;
    }

    private boolean hasPartner(Person person) {
        return person.getPartnerId() != null;
    }

    private boolean exactlyThreeChildrenOfSameParents(List<Person> children, Long partnerId) {
        return exactlyThreeChildren(children) & childrenOfSamePartner(children, partnerId);
    }

    private boolean childrenOfSamePartner(List<Person> children, Long partnerId) {
        return children.stream()
                .allMatch(child -> child.getParentIds().contains(partnerId)
                );
    }

    private boolean exactlyThreeChildren(List<Person> children) {
        return children.size() == 3;
    }

    private boolean hasUnderageChild(List<Person> children) {
        return children.stream().anyMatch(child -> child.getDateOfBirth().isAfter(LocalDate.now().minusYears(18)));
    }
}
