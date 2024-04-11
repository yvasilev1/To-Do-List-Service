package org.example.house.manager.validation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;


@Constraint(validatedBy = StatusValidator.class)
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidateStatus {
    Class<? extends Enum<?>> enumClass();

    String message() default "itemStatus not valid can be - PENDING, COMPLETED, IN_PROGRESS";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
