package nl.hu.inno.humc.courseplanning.presentation.dto;

public class UserInfoDTO {
    private String username;
    private String email;

    public UserInfoDTO(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
}
