package fr.nbrumont.user.service;

import fr.nbrumont.user.database.UserRepository;
import fr.nbrumont.user.mapper.UserMapperImpl;
import fr.nbrumont.user.model.User;
import fr.nbrumont.user.model.UserDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {UserService.class, UserMapperImpl.class})
class UserServiceTest {

    @Autowired
    UserService service;

    @MockBean
    UserRepository repository;

    @Nested
    @DisplayName("Testing find by id behaviours")
    class FindByIdTests {
        @Test
        @DisplayName("When repository returns a user, the response should be a mapped userDTO")
        void correctResponse() {
            // Given
            final Long id = 12L;
            User user = new User();
            user.setId(id);
            user.setUsername("nbrumont");
            user.setBirthDate(LocalDate.of(1990, Month.JANUARY, 1));
            user.setResidenceCountry("France");
            Mockito.doReturn(Optional.of(user)).when(repository).findById(id);

            // When
            Optional<UserDTO> optional = service.findById(id);

            // Then
            Assertions.assertThat(optional).isPresent();
            Assertions.assertThat(optional.get()).isInstanceOf(UserDTO.class);
        }

        @Test
        @DisplayName("When repository does not return a user, service should return optional.empty()")
        void incorrectResponse() {
            // Given
            final Long id = 12L;
            Mockito.doReturn(Optional.empty()).when(repository).findById(id);

            // When
            Optional<UserDTO> optional = service.findById(id);

            // Then
            Assertions.assertThat(optional).isEmpty();
        }
    }
}
