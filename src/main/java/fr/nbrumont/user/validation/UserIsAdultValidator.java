package fr.nbrumont.user.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Clock;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class UserIsAdultValidator implements ConstraintValidator<UserIsAdult, LocalDate> {

    @Autowired
    private Clock clock;

    @Override
    public boolean isValid(LocalDate birthDate, ConstraintValidatorContext constraintValidatorContext) {
        if (birthDate == null) {
            return false;
        }
        LocalDate dateOf18thBirthday = birthDate.plus(18L, ChronoUnit.YEARS);
        return LocalDate.now(clock).atStartOfDay().isAfter(dateOf18thBirthday.minus(1L, ChronoUnit.DAYS).atStartOfDay());
    }
}
