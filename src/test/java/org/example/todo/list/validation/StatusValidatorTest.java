package org.example.todo.list.validation;

import jakarta.validation.ConstraintValidatorContext;
import org.example.todo.list.domain.ItemStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

public class StatusValidatorTest {

    private StatusValidator statusValidator;
    private ConstraintValidatorContext constraintValidatorContext;

    @BeforeEach
    void setUp() {
        statusValidator = new StatusValidator();
        constraintValidatorContext = mock(ConstraintValidatorContext.class);
        statusValidator.setStatuses(List.of(ItemStatus.COMPLETED.name(), ItemStatus.IN_PROGRESS.name(), ItemStatus.PENDING.name()));
    }

    @Test
    public void testIsValid_nullValue_notValid() {
        assertFalse(statusValidator.isValid(null, constraintValidatorContext));
    }

    @Test
    public void testIsValid_blankValue_notValid() {
        assertFalse(statusValidator.isValid("", constraintValidatorContext));
    }

    @Test
    public void testIsValid_emptyValue_notValid() {
        assertFalse(statusValidator.isValid("  ", constraintValidatorContext));
    }

    @Test
    public void testIsValid_unknownValue_notValid() {
        assertFalse(statusValidator.isValid("unknownStatus", constraintValidatorContext));
    }

    @Test
    public void testIsValid_completedStatus_isValid() {
        assertTrue(statusValidator.isValid(ItemStatus.COMPLETED.name(), constraintValidatorContext));
    }

    @Test
    public void testIsValid_inProgressStatus_isValid() {
        assertTrue(statusValidator.isValid(ItemStatus.IN_PROGRESS.name(), constraintValidatorContext));
    }

    @Test
    public void testIsValid_pendingStatus_isValid() {
        assertTrue(statusValidator.isValid(ItemStatus.PENDING.name(), constraintValidatorContext));
    }

    @Test
    public void testIsValid_isCaseInsensitive() {
        assertTrue(statusValidator.isValid(ItemStatus.COMPLETED.name().toLowerCase(), constraintValidatorContext));
        assertTrue(statusValidator.isValid(ItemStatus.IN_PROGRESS.name().toLowerCase(), constraintValidatorContext));
        assertTrue(statusValidator.isValid(ItemStatus.PENDING.name().toLowerCase(), constraintValidatorContext));
    }
}