package my.group.repository;

import my.group.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findUserByUsername(String username);
}
