package org.example.todo.list.validation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Constraint(validatedBy = StatusValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidateStatus {
    String message() default "contact/itemStatus not in valid can be - PENDING, COMPLETED, INPROGRESS";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
