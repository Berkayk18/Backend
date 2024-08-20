package nl.hu.inno.humc.monoliet.course.domain.exceptions;

public class DataAlreadyExistException extends RuntimeException {

    public DataAlreadyExistException(String objectName) {
        super(objectName + " with the given data already exist");
    }
}
