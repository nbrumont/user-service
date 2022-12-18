package fr.nbrumont.user.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.nbrumont.user.database.User;
import fr.nbrumont.user.database.UserRepository;
import org.assertj.core.api.Assertions;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.UnsupportedEncodingException;
import java.time.Clock;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository repository;

    @MockBean
    private Clock clock;

    @BeforeEach
    public void initClock() {
        Clock fixed = Clock.fixed(LocalDate.of(2023, Month.JANUARY, 1).atStartOfDay(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());
        Mockito.doReturn(fixed.instant()).when(clock).instant();
        Mockito.doReturn(fixed.getZone()).when(clock).getZone();
    }

    @AfterEach
    public void cleanUp() {
        repository.deleteAll();
    }

    @Nested
    @DisplayName("Testing saving behaviours")
    @ExtendWith(MockitoExtension.class)
    class SaveTests {

        @Test
        @DisplayName("A correct user should be saved successfully")
        void correctUser() throws Exception {
            // Given
            User user = new User();
            // This user celebrates his 18th birthday "today"
            user.setBirthDate(LocalDate.of(2005, Month.JANUARY, 1));
            user.setUsername("nbrumont");
            user.setResidenceCountry("France");
            MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("")
                    .content(convertToJson(user))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON);

            // When
            ResultActions result = mvc.perform(request);

            // Then
            MvcResult mvcResult = result.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
            User createdUser = convertMVCResultToObject(mvcResult, User.class);
            Assertions.assertThat(createdUser).isNotNull();
            Assertions.assertThat(createdUser).usingRecursiveComparison().ignoringFields("id").isEqualTo(user);
            Assertions.assertThat(createdUser.getId()).isNotNull();
        }

        @Test
        @DisplayName("A user missing mandatory fields should not be created and return a 400")
        void userWithoutMandatoryFields() throws Exception {
            // Given
            User user = new User();
            user.setGender("M");
            user.setPhoneNumber("+33612345679");
            MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("")
                    .content(convertToJson(user))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON);

            // When
            ResultActions result = mvc.perform(request);

            // Then
            result.andExpect(MockMvcResultMatchers.status().is(400))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.username", Is.is("Username is mandatory")))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.birthDate", Is.is("BirthDate is mandatory")))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.residenceCountry", Is.is("ResidenceCountry is mandatory")));
        }


        @Test
        @DisplayName("A user not living in france should not be created and return a 400")
        void userNotLivingInFrance() throws Exception {
            // Given
            User user = new User();
            user.setBirthDate(LocalDate.of(1990, Month.APRIL, 2));
            user.setUsername("nbrumont");
            user.setResidenceCountry("Canada");
            user.setGender("M");
            user.setPhoneNumber("+33612345679");
            MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("")
                    .content(convertToJson(user))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON);

            // When
            ResultActions result = mvc.perform(request);

            // Then
            result.andExpect(MockMvcResultMatchers.status().is(400))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.residenceCountry", Is.is("User must live in France")));
        }

        @Test
        @DisplayName("A non-adult user should not be created and return a 400")
        void nonAdultuser() throws Exception {
            // Given
            User user = new User();
            user.setBirthDate(LocalDate.of(2005, Month.JANUARY, 2));
            user.setUsername("nbrumont");
            user.setResidenceCountry("France");
            user.setGender("M");
            user.setPhoneNumber("+33612345679");
            MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("")
                    .content(convertToJson(user))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON);

            // When
            ResultActions result = mvc.perform(request);

            // Then
            result.andExpect(MockMvcResultMatchers.status().is(400))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.birthDate", Is.is("User must be adult")));
        }


        @Test
        @DisplayName("A user with an incorrect phone number should not be created and return 400")
        void incorrectPhoneNumber() throws Exception {
            // Given
            User user = new User();
            user.setBirthDate(LocalDate.of(2005, Month.JANUARY, 1));
            user.setUsername("nbrumont");
            user.setResidenceCountry("France");
            user.setGender("M");
            user.setPhoneNumber("+35612345679");
            MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("")
                    .content(convertToJson(user))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON);

            // When
            ResultActions result = mvc.perform(request);

            // Then
            result.andExpect(MockMvcResultMatchers.status().is(400))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.phoneNumber", Is.is("PhoneNumber should start with +33 or 0 plus 9 numbers")));
        }
    }

    @Nested
    @DisplayName("Testing reading behaviours")
    class ReadTests {

        @Test
        @DisplayName("Requesting a valid user should return it")
        void findUser() throws Exception {
            // Given
            User user = new User();
            user.setBirthDate(LocalDate.of(2005, Month.JANUARY, 1));
            user.setUsername("nbrumont");
            user.setResidenceCountry("France");
            user.setPhoneNumber("+33623456789");
            user.setGender("M");

            User createdUser = repository.save(user);

            MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/" + createdUser.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON);

            // When
            MvcResult result = mvc.perform(request).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            // Then
            User returnedUser = convertMVCResultToObject(result, User.class);
            Assertions.assertThat(returnedUser).isNotNull();
            Assertions.assertThat(returnedUser).usingRecursiveComparison().isEqualTo(createdUser);
        }

        @Test
        @DisplayName("Requesting a user that does not exist should return a 204")
        void findNonExistingUser() throws Exception {
            // Given
            MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/12")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON);

            // When
            ResultActions result = mvc.perform(request);

            // Then
            result.andExpect(MockMvcResultMatchers.status().is(204));
        }

        @Test
        @DisplayName("Requesting a user with a wrong id format should return a 400")
        void findWithWrongIdFormat() throws Exception {
            // Given
            MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/abc")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON);

            // When
            ResultActions result = mvc.perform(request);

            // Then
            result.andExpect(MockMvcResultMatchers.status().is(400));
        }
    }

    private String convertToJson(final Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private <T> T convertMVCResultToObject(final MvcResult result, Class<T> clazz) throws UnsupportedEncodingException, JsonProcessingException {
        return objectMapper.readValue(result.getResponse().getContentAsString(), clazz);
    }
}
