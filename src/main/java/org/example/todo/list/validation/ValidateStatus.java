package org.example.todo.list.validation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;


@Constraint(validatedBy = StatusValidator.class)
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidateStatus {
    Class<? extends Enum<?>> enumClass();

    String message() default "contact/itemStatus not in valid can be - PENDING, COMPLETED, IN_PROGRESS";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
