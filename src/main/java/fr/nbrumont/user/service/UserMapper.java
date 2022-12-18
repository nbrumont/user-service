package fr.nbrumont.user.service;

import fr.nbrumont.user.api.UserDTO;
import fr.nbrumont.user.api.UserForCreationDTO;
import fr.nbrumont.user.database.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    User mapUserForCreationDTOToUser(final UserForCreationDTO user);

    UserDTO mapUserToDTO(final User user);
}
