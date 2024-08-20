package nl.hu.inno.humc.monoliet.course.application.dto;

import nl.hu.inno.humc.monoliet.cursusverloop.presentation.dto.AnnouncementDTO;

import java.util.List;

public record CourseDTO(List<AnnouncementDTO> announcementDTOs, List<CourseMaterialDTO> courseMaterialDTOs, String title, String period, String branche) {
    public static class Builder {
        private List<AnnouncementDTO> announcementDTOs;
        private List<CourseMaterialDTO> courseMaterialDTOs;
        private String title;
        private String period;
        private String branche;

        public Builder announcementDTOs(List<AnnouncementDTO> announcementDTOs) {
            this.announcementDTOs = announcementDTOs;
            return this;
        }

        public Builder courseMaterialDTOs(List<CourseMaterialDTO> courseMaterialDTOs) {
            this.courseMaterialDTOs = courseMaterialDTOs;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder period(String period) {
            this.period = period;
            return this;
        }

        public Builder branche(String branche) {
            this.branche = branche;
            return this;
        }

        public CourseDTO build() {
            return new CourseDTO(announcementDTOs, courseMaterialDTOs, title, period, branche);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}