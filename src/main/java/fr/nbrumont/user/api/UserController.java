package fr.nbrumont.user.api;

import fr.nbrumont.user.model.UserDTO;
import fr.nbrumont.user.model.UserForCreationDTO;
import fr.nbrumont.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserController {

    private final UserService service;

    /**
     * POST api allowing the creation of a new {@link UserForCreationDTO}
     * The body must be set, and fields with controls must be correct
     *
     * @param user {@link UserForCreationDTO} the user to create
     * @return a {@link ResponseEntity} containing the created user, or a 400 status response if validation fails.
     */
    @PostMapping(path = {"/", ""})
    public ResponseEntity<UserDTO> save(@Valid @RequestBody UserForCreationDTO user) {
        UserDTO savedUser = service.save(user);
        return ResponseEntity.ok(savedUser);
    }

    /**
     * GET api allowing the reading of an existing user.
     *
     * @param id {@link Long}, the id of the wanted user
     * @return a {@link ResponseEntity} containing the retrieved user, or a 404 status response if the user does not exist.
     */
    @GetMapping(path = "/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable("id") Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    /**
     * Creates a specific error message when the user is missing from the body of the POST request ({@link HttpMessageNotReadableException})
     * The response will have a 400 statut and a json object containing the message "Body with user is mandatory"
     *
     * @return a {@link ResponseEntity} containing a map { message: reason }
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    private Map<String, String> handleMessageNotReadableException() {
        Map<String, String> errors = new HashMap<>();
        errors.put("message", "Body with user is mandatory");
        return errors;
    }

}
