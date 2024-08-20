package nl.hu.inno.humc.monoliet.course.domain.exceptions;

public class LimitExceededException extends RuntimeException {

    public LimitExceededException(String objectName) {
        super("Cannot add more " + objectName+", the maximum limit has been reached.");
    }
}
