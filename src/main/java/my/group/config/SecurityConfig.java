package my.group.config;


import my.group.model.Response;
import my.group.service.UserService;
import my.group.utility.JsonConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JsonConverter jsonConverter = new JsonConverter();
    private UserService userService;

    @Autowired
    MessageSource messageSource;

    @Autowired
    public SecurityConfig setUserService(UserService userService) {
        this.userService = userService;
        return this;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/api/admin/**").hasRole("ADMIN")
                .antMatchers("/api/public/**").hasAnyRole("USER", "ADMIN")
                .antMatchers("/h2-console/**").permitAll()
                .and().formLogin()
                .defaultSuccessUrl("/swagger-ui/index.html#")
                .and()
                .exceptionHandling()
                .accessDeniedHandler((request, response, accessDeniedException) -> {
                    response.setStatus(HttpStatus.FORBIDDEN.value());
                    response.setContentType("application/json");

                    String uri = request.getRequestURI();
                    String userName = request.getRemoteUser();
                    String userRole = userService.findByUserName(userName).getRoles().toString();
                    String message = messageSource.getMessage("security.authentication",
                            new Object[]{userName, userRole, uri}, LocaleContextHolder.getLocale());

                    response.getWriter().write(jsonConverter.createJsonFromObjects(new Response(null, uri, HttpStatus.FORBIDDEN, message)));
                });

        super.configure(http);
        http.csrf().disable();
        http.headers().frameOptions().disable();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(userService);
        return authenticationProvider;
    }
}
