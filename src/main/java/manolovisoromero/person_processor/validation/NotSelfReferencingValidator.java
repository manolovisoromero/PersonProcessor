package manolovisoromero.person_processor.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import manolovisoromero.person_processor.model.Person;

public class NotSelfReferencingValidator implements ConstraintValidator<NotSelfReferencing, Person> {

    @Override
    public boolean isValid(Person person, ConstraintValidatorContext context) {
        if (person == null) return true;

        Long id = person.getId();
        return !(person.getParentIds().contains(id) || person.getChildrenIds().contains(id));
    }
}
