package my.group.controller;

import my.group.dto.UserDto;
import my.group.dto.UserDtoForAdd;
import my.group.exception.UserConflictException;
import my.group.model.Response;
import my.group.model.Role;
import my.group.model.User;
import my.group.repository.UserRepository;
import my.group.service.TaskService;
import my.group.service.UserService;
import my.group.utility.JsonConverter;
import org.hibernate.validator.constraints.Length;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.security.Principal;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

@RestController
@RequestMapping("api/public/my")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    @Autowired
    MessageSource messageSource;

    private final JsonConverter jsonConverter = new JsonConverter();

    @GetMapping("")
    public ResponseEntity<String> getRole(Principal principal) {
        User user = userRepository.findUserByUsername(principal.getName()).orElseThrow(() -> new UsernameNotFoundException(String.format("User %s not found", principal.getName())));
        UserDto userDto = new UserDto(user.getUsername(), user.getRoles());
        String json = jsonConverter.createJsonFromObjects(userDto);
        String message = messageSource.getMessage("my.getInfo", null, LocaleContextHolder.getLocale());
        return ResponseEntity.ok(new Response(json, HttpStatus.OK, message).toString());
    }

    @PostMapping("")
    public ResponseEntity<String> addUser(@RequestBody UserDtoForAdd dtoForAdd) {
        String json = userService.addUserToDb(dtoForAdd);
        String message = messageSource.getMessage("my.userAdd", null, LocaleContextHolder.getLocale());
        return ResponseEntity.ok(new Response(json, HttpStatus.OK, message).toString());


    }
//
//    private String addUserToDb(UserDtoForAdd dtoForAdd){
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        String userName = dtoForAdd.getUserName();
//        String password = passwordEncoder.encode(dtoForAdd.getPassword());
//        User user = userRepository.findUserByUsername(userName).orElse(null);
//        if(user==null){
//            User newUser = new User(userName,password, Collections.singletonList(new Role("ROLE_USER")));
//            UserDto userDto = new UserDto(userName, newUser.getRoles());
//            String json = jsonConverter.createJsonFromObjects(userDto);
//            userRepository.save(newUser);
//            return json;
//            }else throw new UserConflictException("A user with this username is already registered, please select a different username.",
//                "api/public/my",userName  );
//
//    }

}
