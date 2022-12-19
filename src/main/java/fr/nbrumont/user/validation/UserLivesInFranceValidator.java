package fr.nbrumont.user.validation;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class UserLivesInFranceValidator implements ConstraintValidator<UserLivesInFrance, String> {

    private static final Set<String> ACCEPTED_COUNTRIES = Set.of("FRANCE", "FRA");

    /**
     * Checks that the input is allowed as a representation of France ({@link #ACCEPTED_COUNTRIES})
     *
     * @param residenceCountry           the {@link String} to be checked
     * @param constraintValidatorContext constraintValidatorContext the constraintContext, not used here but needed from the signature
     * @return a boolean, true if validation passes, false if it fails
     */
    @Override
    public boolean isValid(String residenceCountry, ConstraintValidatorContext constraintValidatorContext) {
        return residenceCountry != null && ACCEPTED_COUNTRIES.stream().anyMatch(residenceCountry::equalsIgnoreCase);
    }
}
