package my.group.testing;


import my.group.model.Role;
import my.group.model.User;
import my.group.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.Collections;

@DataJpaTest
class UserRepositoryTest {
    @Autowired
    UserRepository repository;

    @Test
    void testSaveFindDeleteStudent() {
        User pasha = new User("pasha","pass1", Collections.singletonList(new Role("ROLE_USER")));
        User masha = new User("masha","pass2", Collections.singletonList(new Role("ROLE_ADMIN")));
        User sasha = new User("sasha","pass3", Arrays.asList(new Role("ROLE_USER"),new Role("ROLE_ADMIN")));
        repository.save(pasha);
        repository.save(masha);
        repository.save(sasha);

        User findMasha = repository.findUserByUsername("masha").get();
        User findPasha = repository.findUserByUsername("pasha").get();
        User findSasha = repository.findUserByUsername("sasha").get();
        Assertions.assertEquals(masha.getUsername(), findMasha.getUsername());
        Assertions.assertEquals(sasha.getUsername(), findSasha.getUsername());
        Assertions.assertEquals(pasha.getUsername(), findPasha.getUsername());
        repository.deleteById(1L);

    }


}