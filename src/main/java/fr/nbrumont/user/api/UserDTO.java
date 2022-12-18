package fr.nbrumont.user.api;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserDTO extends UserForCreationDTO {
    private Long id;
}
