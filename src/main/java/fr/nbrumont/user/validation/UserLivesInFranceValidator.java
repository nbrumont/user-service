package fr.nbrumont.user.validation;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class UserLivesInFranceValidator implements ConstraintValidator<UserLivesInFrance, String> {

    private static final Set<String> ACCEPTED_COUNTRIES = Set.of("FRANCE", "FRA");

    @Override
    public boolean isValid(String residenceCountry, ConstraintValidatorContext constraintValidatorContext) {
        return residenceCountry != null && ACCEPTED_COUNTRIES.stream().anyMatch(residenceCountry::equalsIgnoreCase);
    }
}
