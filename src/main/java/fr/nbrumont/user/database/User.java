package fr.nbrumont.user.database;

import fr.nbrumont.user.validation.UserIsAdult;
import fr.nbrumont.user.validation.UserLivesInFrance;
import fr.nbrumont.user.validation.ValidationSecondOrder;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.GroupSequence;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Table(name = "users")
@Entity
@GroupSequence({User.class, ValidationSecondOrder.class})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Username is mandatory")
    @Column(name = "username", nullable = false)
    private String username;

    @UserIsAdult(groups = ValidationSecondOrder.class)
    @NotNull(message = "BirthDate is mandatory")
    @Temporal(value = TemporalType.DATE)
    @Column(name = "birthdate", nullable = false)
    private LocalDate birthDate;

    @UserLivesInFrance(groups = ValidationSecondOrder.class)
    @NotEmpty(message = "ResidenceCountry is mandatory")
    @Column(name = "residenceCountry", nullable = false)
    private String residenceCountry;

    @Pattern(regexp = "^(\\+33|0)\\d{9}$", message = "PhoneNumber should start with +33 or 0 plus 9 numbers")
    @Column(name = "phoneNumber")
    private String phoneNumber;

    @Column(name = "gender")
    private String gender;
}
