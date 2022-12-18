package fr.nbrumont.user.validation;

import fr.nbrumont.user.config.ClockConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Clock;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {UserIsAdultValidator.class, ClockConfig.class})
class UserIsAdultValidatorTest {

    @Autowired
    UserIsAdultValidator validator;

    @MockBean
    private Clock clock;

    @BeforeEach
    public void initClock() {
        Clock fixed = Clock.fixed(LocalDate.of(2023, Month.JANUARY, 1).atStartOfDay(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());
        Mockito.doReturn(fixed.instant()).when(clock).instant();
        Mockito.doReturn(fixed.getZone()).when(clock).getZone();
    }

    @Test
    @DisplayName("Valid birthDate should be accepted")
    void validBirthDate() {
        // Given
        LocalDate birthDate = LocalDate.of(2005, Month.JANUARY, 1);

        // When
        boolean isValid = validator.isValid(birthDate, null);

        // Then
        Assertions.assertThat(isValid).isTrue();
    }

    @Nested
    @DisplayName("Testing invalid behaviours")
    class InvalidDates {
        @Test
        @DisplayName("Null birthDate should be rejected")
        void nullDate() {
            // Given
            LocalDate birthDate = null;

            // When
            boolean isValid = validator.isValid(birthDate, null);

            // Then
            Assertions.assertThat(isValid).isFalse();
        }


        @Test
        @DisplayName("BirthDate less than 18 years ago should be rejected")
        void tooRecentDate() {
            // Given
            LocalDate birthDate = LocalDate.of(2005, Month.JANUARY, 2);

            // When
            boolean isValid = validator.isValid(birthDate, null);

            // Then
            Assertions.assertThat(isValid).isFalse();
        }
    }

}