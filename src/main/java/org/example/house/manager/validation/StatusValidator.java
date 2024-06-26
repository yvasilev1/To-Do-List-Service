package org.example.house.manager.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Setter
public class StatusValidator implements ConstraintValidator<ValidateStatus, String> {
    private List<String> statuses;

    @Override
    public void initialize(ValidateStatus constraintAnnotation) {
        statuses = Stream.of(constraintAnnotation.enumClass().getEnumConstants())
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return value != null && statuses.contains(value.toUpperCase());
    }
}
