package org.example.todo.list.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.example.todo.list.domain.ItemStatus;


public class StatusValidator implements ConstraintValidator<ValidateStatus, ItemStatus> {
    @Override
    public boolean isValid(ItemStatus itemStatus, ConstraintValidatorContext constraintValidatorContext) {

        return itemStatus != null && ItemStatus.containsStatus(itemStatus);
    }
}
