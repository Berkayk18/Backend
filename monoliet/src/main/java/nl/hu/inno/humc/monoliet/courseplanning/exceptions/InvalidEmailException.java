package nl.hu.inno.humc.monoliet.courseplanning.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidEmailException extends RuntimeException {
    public InvalidEmailException(String email) {
        super("Invalid HU email address: " + email);
    }
}
