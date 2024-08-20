package nl.hu.inno.humc.courseplanning.application.dto;

import java.util.UUID;

public class RegisterStudentDTO {
    private UUID id;
    private UUID studyId;


    public RegisterStudentDTO(UUID id, UUID studyId) {
        this.id = id;
        this.studyId = studyId;
    }

    public UUID getId() {
        return id;
    }

    public UUID getStudyId() {
        return studyId;
    }
}
