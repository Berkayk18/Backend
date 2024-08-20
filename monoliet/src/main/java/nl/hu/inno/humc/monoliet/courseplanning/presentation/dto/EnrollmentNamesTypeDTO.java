package nl.hu.inno.humc.monoliet.courseplanning.presentation.dto;

import nl.hu.inno.humc.monoliet.courseplanning.domain.Enrollment;

public class EnrollmentNamesTypeDTO {
    private String username;
    private Enrollment.Type type;

    public EnrollmentNamesTypeDTO(Enrollment.Type type, String username) {
        this.type = type;
        this.username = username;
    }
}
