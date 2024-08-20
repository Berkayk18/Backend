package nl.hu.inno.humc.courseplanning.presentation.dto;

import nl.hu.inno.humc.courseplanning.domain.Email;

public class UserDTO {
    private String username;
    private Email email;

    public UserDTO(String username, Email email) {
        this.username = username;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public Email getEmail() {
        return email;
    }
}
