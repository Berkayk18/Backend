package nl.hu.inno.humc.monoliet.course.application.dto;

import java.util.List;

public record CourseMaterialDTO(List<ModuleDTO> moduleDTOList, String subject) {

    public static class Builder {
        private List<ModuleDTO> moduleDTOList;
        private String subject;

        public Builder moduleDTOList(List<ModuleDTO> moduleDTOList) {
            this.moduleDTOList = moduleDTOList;
            return this;
        }

        public Builder subject(String subject) {
            this.subject = subject;
            return this;
        }

        public CourseMaterialDTO build() {
            return new CourseMaterialDTO(moduleDTOList, subject);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
