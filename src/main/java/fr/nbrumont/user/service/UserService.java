package fr.nbrumont.user.service;

import fr.nbrumont.user.database.UserRepository;
import fr.nbrumont.user.mapper.UserMapper;
import fr.nbrumont.user.model.User;
import fr.nbrumont.user.model.UserDTO;
import fr.nbrumont.user.model.UserForCreationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserService {
    private final UserMapper mapper;
    private final UserRepository repository;

    /**
     * Uses {@link UserMapper} and {@link UserRepository} to correctly create and save a {@link User} in database
     *
     * @param user, the {@link UserForCreationDTO} to save
     * @return the {@link UserDTO} saved
     */
    public UserDTO save(final UserForCreationDTO user) {
        final User userToCreate = mapper.mapUserForCreationDTOToUser(user);
        final User createdUser = repository.save(userToCreate);
        return mapper.mapUserToDTO(createdUser);
    }

    /**
     * Uses {@link UserMapper} and {@link UserRepository} to attempt to retrieve a {@link User} from the database
     *
     * @param id {@link Long}, the id of the user we try to retrieve
     * @return an {@link Optional} containing either the {@link UserDTO} found (and mapped) or containing empty() if no user is found
     */
    public Optional<UserDTO> findById(final Long id) {
        return repository.findById(id).map(mapper::mapUserToDTO).or(Optional::empty);
    }
}
