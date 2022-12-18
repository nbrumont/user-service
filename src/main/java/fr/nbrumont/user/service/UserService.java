package fr.nbrumont.user.service;

import fr.nbrumont.user.api.UserDTO;
import fr.nbrumont.user.api.UserForCreationDTO;
import fr.nbrumont.user.database.User;
import fr.nbrumont.user.database.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserService {
    private final UserMapper mapper;
    private final UserRepository repository;

    public UserDTO save(final UserForCreationDTO user) {
        User userToCreate = mapper.mapUserForCreationDTOToUser(user);
        User createdUser = repository.save(userToCreate);
        return mapper.mapUserToDTO(createdUser);
    }

    public Optional<UserDTO> findById(final Long id) {
        return repository.findById(id).map(mapper::mapUserToDTO).or(Optional::empty);
    }
}
