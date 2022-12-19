package fr.nbrumont.user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
public class ClockConfig {

    /**
     * Allows the injection of a default clock, mostly for using with LocalDate.now(clock)
     * This facilitates the mocking of LocalDate.now() to have tests not depending on execution date.
     *
     * @return the system default zoned {@link Clock}
     */
    @Bean
    Clock clock() {
        return Clock.systemDefaultZone();
    }
}
