package fr.nbrumont.user.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Component
public class UserIsAdultValidator implements ConstraintValidator<UserIsAdult, LocalDate> {

    @Autowired
    private Clock clock;

    /**
     * Checks that a date is not null and is older than 18 years - 1 day from today (we allow for the 18th birthday to be today)
     *
     * @param birthDate                  the {@link LocalDate} to check
     * @param constraintValidatorContext the constraintContext, not used here but needed from the signature
     * @return a boolean, true if validation passes, false if it fails
     */
    @Override
    public boolean isValid(LocalDate birthDate, ConstraintValidatorContext constraintValidatorContext) {
        if (birthDate == null) {
            return false;
        }
        LocalDate dateOf18thBirthday = birthDate.plus(18L, ChronoUnit.YEARS);
        return LocalDate.now(clock).atStartOfDay().isAfter(dateOf18thBirthday.minus(1L, ChronoUnit.DAYS).atStartOfDay());
    }
}
