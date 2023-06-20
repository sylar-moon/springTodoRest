package my.group.controler;

import my.group.dto.UserDto;
import my.group.model.Response;
import my.group.model.User;
import my.group.repository.UserRepository;
import my.group.utility.JsonConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("api/public/my")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    MessageSource messageSource;

    @GetMapping("")
    public ResponseEntity<String> getRole(Principal principal) {
        User user = userRepository.findUserByUsername(principal.getName());
        UserDto userDto = new UserDto(user.getUsername(), user.getRoles());
        String json = new JsonConverter().createJsonFromObjects(userDto);
        String message = messageSource.getMessage("my.getInfo",null, LocaleContextHolder.getLocale());
        return ResponseEntity.ok(new Response(json, "public/my", HttpStatus.OK,message).toString());
    }

}
