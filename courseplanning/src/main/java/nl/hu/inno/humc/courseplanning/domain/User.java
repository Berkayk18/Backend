package nl.hu.inno.humc.courseplanning.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document(collection = "users")
public class User {
    @Id
    private UUID id;

    private String name;

    private Email email;

    protected User() {
    }

    public User(String name, Email email) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.email = email;
    }

    public User(UUID id,String name, Email email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public UUID getId() {
        return id;
    }

    public Email getEmail() {
        return email;
    }
}
