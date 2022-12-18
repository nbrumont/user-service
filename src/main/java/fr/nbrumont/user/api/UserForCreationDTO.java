package fr.nbrumont.user.api;

import fr.nbrumont.user.validation.UserIsAdult;
import fr.nbrumont.user.validation.UserLivesInFrance;
import fr.nbrumont.user.validation.ValidationSecondOrder;
import jakarta.validation.GroupSequence;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@GroupSequence({UserForCreationDTO.class, ValidationSecondOrder.class})
public class UserForCreationDTO {
    @NotEmpty(message = "Username is mandatory")
    private String username;

    @UserIsAdult(groups = ValidationSecondOrder.class)
    @NotNull(message = "BirthDate is mandatory")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate birthDate;

    @UserLivesInFrance(groups = ValidationSecondOrder.class)
    @NotEmpty(message = "ResidenceCountry is mandatory")
    private String residenceCountry;

    @Pattern(regexp = "^(\\+33|0)\\d{9}$", message = "PhoneNumber should start with +33 or 0 plus 9 numbers")
    private String phoneNumber;

    private String gender;
}
