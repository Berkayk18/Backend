package nl.hu.inno.humc.monoliet.courseplanning.presentation.dto;

import nl.hu.inno.humc.monoliet.courseplanning.domain.Enrollment;

public class EnrollmentUserIDTypeDTO {
    private long userId;
    private Enrollment.Type type;

    public EnrollmentUserIDTypeDTO(long userId, Enrollment.Type type) {
        this.userId = userId;
        this.type = type;
    }

    public long getUserId() {
        return userId;
    }

    public Enrollment.Type getType() {
        return type;
    }
}
