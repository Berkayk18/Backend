package nl.hu.inno.humc.courseplanning.presentation.dto;

import nl.hu.inno.humc.courseplanning.domain.Enrollment;

import java.util.UUID;

public class EnrollmentUserIDTypeDTO {
    private UUID userId;
    private Enrollment.Type type;

    public EnrollmentUserIDTypeDTO(UUID userId, Enrollment.Type type) {
        this.userId = userId;
        this.type = type;
    }

    public UUID getUserId() {
        return userId;
    }

    public Enrollment.Type getType() {
        return type;
    }
}
