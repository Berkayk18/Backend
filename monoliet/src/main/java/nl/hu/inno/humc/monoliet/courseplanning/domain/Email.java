package nl.hu.inno.humc.monoliet.courseplanning.domain;

import jakarta.persistence.Embeddable;
import nl.hu.inno.humc.monoliet.courseplanning.exceptions.InvalidEmailException;

import java.util.Objects;
import java.util.regex.Pattern;

@Embeddable
public class Email {

    private static final Pattern HU_EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]+@(student\\.)?hu\\.nl$");

    private String email;

    protected Email() {
    }

    public Email(String email) {
        if (!isValid(email)) {
            throw new InvalidEmailException(email);
        }
        this.email = email;
    }

    private static boolean isValid(String email) {
        return HU_EMAIL_PATTERN.matcher(email).matches();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Email email1)) return false;
        return Objects.equals(email, email1.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
