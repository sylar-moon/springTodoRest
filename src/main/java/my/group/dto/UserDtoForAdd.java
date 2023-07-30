package my.group.dto;

public class UserDtoForAdd {
    private String userName;
    private String password;

    public UserDtoForAdd(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public UserDtoForAdd setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserDtoForAdd setPassword(String password) {
        this.password = password;
        return this;
    }
}
