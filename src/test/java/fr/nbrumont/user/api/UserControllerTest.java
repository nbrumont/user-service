package fr.nbrumont.user.api;

import fr.nbrumont.user.model.UserDTO;
import fr.nbrumont.user.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {UserController.class})
public class UserControllerTest {

    @Autowired
    UserController controller;

    @MockBean
    UserService service;

    @Nested
    @DisplayName("Testing find by id behaviours")
    class FindByIdTests {
        @Test
        @DisplayName("When service returns a user, the response should be correct")
        void correctResponse() {
            // Given
            final Long id = 12L;
            UserDTO user = new UserDTO();
            user.setId(id);
            user.setUsername("nbrumont");
            user.setBirthDate(LocalDate.of(1990, Month.JANUARY, 1));
            user.setResidenceCountry("France");
            Mockito.doReturn(Optional.of(user)).when(service).findById(id);

            // When
            ResponseEntity<UserDTO> response = controller.findById(id);

            // Then
            Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            Assertions.assertThat(response.getBody()).isEqualTo(user);
        }

        @Test
        @DisplayName("When service does not return a user, an exception should be thrown")
        void incorrectResponse() {
            // Given
            final Long id = 12L;
            Mockito.doReturn(Optional.empty()).when(service).findById(id);

            // When
            Exception exception = Assertions.catchException(() -> controller.findById(id));

            // Then
            Assertions.assertThat(exception).isInstanceOf(ResponseStatusException.class);
            ResponseStatusException castedException = (ResponseStatusException) exception;
            Assertions.assertThat(castedException.getReason()).isEqualTo("User not found");
            Assertions.assertThat(castedException.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        }
    }
}
