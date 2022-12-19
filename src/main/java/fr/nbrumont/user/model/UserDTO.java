package fr.nbrumont.user.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO extends UserForCreationDTO {
    private Long id;
}
