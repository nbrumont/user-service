package fr.nbrumont.user.api;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO extends UserForCreationDTO {
    private Long id;
}
