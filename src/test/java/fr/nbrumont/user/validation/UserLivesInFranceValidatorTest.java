package fr.nbrumont.user.validation;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {UserLivesInFranceValidator.class})
class UserLivesInFranceValidatorTest {
    @Autowired
    UserLivesInFranceValidator validator;

    @ParameterizedTest(name = "{0} should be a valid input")
    @ValueSource(strings = {"France", "FRANCE", "FRA", "fra"})
    void validCountry(final String country) {
        // When
        boolean isValid = validator.isValid(country, null);

        // Then
        Assertions.assertThat(isValid).isTrue();
    }

    @ParameterizedTest(name = "{0} should not be a valid input")
    @NullAndEmptySource
    @ValueSource(strings = {"canada", "China", "abd"})
    void invalidCountry(final String country) {
        // When
        boolean isValid = validator.isValid(country, null);

        // Then
        Assertions.assertThat(isValid).isFalse();
    }
}