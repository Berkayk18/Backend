package nl.hu.inno.humc.courseplanning.domain;

import nl.hu.inno.humc.courseplanning.exceptions.InvalidEmailException;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.Objects;
import java.util.regex.Pattern;

public class Email {

    private static final Pattern HU_EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]+@(student\\.)?hu\\.nl$");

    @Indexed(unique = true)
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

    @Override
    public String toString() {
        return email;
    }
}
