package my.group.dto;

import my.group.model.Role;

import java.util.Collection;

public class UserDto {
    private String username;
    private Collection<Role> roles;

    public UserDto(String username, Collection<Role> roles) {
        this.username = username;
        this.roles = roles;
    }

    public UserDto() {
    }

    public String getUsername() {
        return username;
    }

    public UserDto setUsername(String username) {
        this.username = username;
        return this;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public UserDto setRoles(Collection<Role> roles) {
        this.roles = roles;
        return this;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "username='" + username + '\'' +
                ", roles=" + roles +
                '}';
    }
}
