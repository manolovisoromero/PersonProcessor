package manolovisoromero.person_processor.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotSelfReferencingValidator.class)
public @interface NotSelfReferencing {
    String message() default "Person cannot be their own parent or child";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
