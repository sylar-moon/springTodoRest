package my.group.service;

import my.group.dto.UserDto;
import my.group.dto.UserDtoForAdd;
import my.group.exception.UserConflictException;
import my.group.model.Role;
import my.group.model.User;
import my.group.repository.UserRepository;
import my.group.utility.JsonConverter;
import my.group.utility.MyLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    private final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final JsonConverter jsonConverter = new JsonConverter();

    @Autowired
    private UserRepository repository;

    public User findByUserName(String username){
        User user = repository.findUserByUsername(username).orElseThrow(()->new UsernameNotFoundException(String.format("User %s not found", username)));
        logger.info(user.toString());

        return user;
    }
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUserName(username);
        if(user==null){
            throw new UsernameNotFoundException(String.format("User %s not found",username));
        }
        logger.info("{} {} {} {}",user.getUsername(),user.getPassword(),user.getRoles(),mapRolesAuthorities(user.getRoles()));
        return new org.springframework.security.core.userdetails
                .User(user.getUsername(),user.getPassword(),mapRolesAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesAuthorities (Collection<Role> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }


    public String addUserToDb(UserDtoForAdd dtoForAdd){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String userName = dtoForAdd.getUserName();
        String password = passwordEncoder.encode(dtoForAdd.getPassword());
        User user = repository.findUserByUsername(userName).orElse(null);
        if(user==null){
            User newUser = new User(userName,password, Collections.singletonList(new Role("ROLE_USER")));
            UserDto userDto = new UserDto(userName, newUser.getRoles());
            String json = jsonConverter.createJsonFromObjects(userDto);
            repository.save(newUser);
            return json;
        }else throw new UserConflictException("A user with this username is already registered, please select a different username.",
                "api/public/my",userName  );

    }

}
