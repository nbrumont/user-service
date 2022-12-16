package fr.nbrumont.user.database;

import fr.nbrumont.user.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
