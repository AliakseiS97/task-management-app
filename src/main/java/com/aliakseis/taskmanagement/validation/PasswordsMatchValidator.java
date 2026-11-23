package com.aliakseis.taskmanagement.validation;

import com.aliakseis.taskmanagement.dto.user.UserRegistrationRequestDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Objects;

public class PasswordsMatchValidator
        implements ConstraintValidator<PasswordsMatch, UserRegistrationRequestDto> {
    private static final String REPEAT_PASSWORD_FIELD = "repeatPassword";

    @Override
    public boolean isValid(UserRegistrationRequestDto dto, ConstraintValidatorContext context) {
        if (dto == null || Objects.equals(dto.password(), dto.repeatPassword())) {
            return true;
        }
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                .addPropertyNode(REPEAT_PASSWORD_FIELD)
                .addConstraintViolation();
        return false;
    }
}
