package nl.hu.inno.humc.monoliet.course.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Enrollment {
    @Id
    @GeneratedValue
    private Long id;

    private String username;

    private String userTpe;

    public Enrollment(String username, String userTpe) {
        this.username = username;
        this.userTpe = userTpe;
    }

    protected Enrollment() {}

    protected Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getUserTpe() {
        return userTpe;
    }
}
