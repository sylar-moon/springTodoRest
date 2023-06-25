package my.group.model;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Collection;

@Table(name = "user",
        uniqueConstraints = @UniqueConstraint(columnNames = "username"))
@Entity

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    @NotBlank
    private String username;
    @Column
    @NotBlank
    private String password;

    @ManyToMany(fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
    @JoinTable(name = "users_roles",joinColumns =
    @JoinColumn(name = "user_id",referencedColumnName = "id"),inverseJoinColumns =
    @JoinColumn(name = "role_id",referencedColumnName = "id"))
    private Collection<Role> roles;

    public User(String username, String password, Collection<Role> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public User() {
    }

    public String getUsername() {
        return username;
    }

    public User setUsername(String userName) {
        this.username = userName;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public User setRoles(Collection<Role> roles) {
        this.roles = roles;
        return this;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                '}';
    }
}