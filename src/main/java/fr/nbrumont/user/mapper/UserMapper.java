package fr.nbrumont.user.mapper;

import fr.nbrumont.user.model.User;
import fr.nbrumont.user.model.UserDTO;
import fr.nbrumont.user.model.UserForCreationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    /**
     * Creates a new {@link User} from a {@link UserForCreationDTO} to allow for user creation in database
     * If null is passed, null is returned.
     *
     * @param user, the {@link UserForCreationDTO} to map
     * @return the created {@link User}
     */
    @Mapping(target = "id", ignore = true)
    User mapUserForCreationDTOToUser(final UserForCreationDTO user);

    /**
     * Creates a new {@link UserDTO} from a {@link User} to allow for safe reading from database and for read APIs usages
     * If null is passed, null is returned.
     *
     * @param user, the {@link User} to map
     * @return the created {@link UserDTO}
     */
    UserDTO mapUserToDTO(final User user);
}
