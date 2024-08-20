package nl.hu.inno.humc.courseplanning.presentation.dto;

import nl.hu.inno.humc.courseplanning.domain.Enrollment;

public class EnrollmentNamesTypeDTO {
    private String username;
    private Enrollment.Type type;

    public EnrollmentNamesTypeDTO(Enrollment.Type type, String username) {
        this.type = type;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public Enrollment.Type getType() {
        return type;
    }
}
