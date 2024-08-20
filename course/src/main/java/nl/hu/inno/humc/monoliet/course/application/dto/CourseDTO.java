package nl.hu.inno.humc.monoliet.course.application.dto;

import java.util.List;

public record CourseDTO(List<AnnouncementDTO> announcementDTOs, List<EnrollmentDTO> enrollmentDTOs, List<CourseMaterialDTO> courseMaterialDTOs,
                        String title, String period, String branche, int announcementsCount, int enrollmentsCount) {
    public static class Builder {
        private List<AnnouncementDTO> announcementDTOs;
        private List<EnrollmentDTO> enrollmentDTOs;
        private List<CourseMaterialDTO> courseMaterialDTOs;
        private String title;
        private String period;
        private String branche;

        private int announcementsCount;
        private int enrollmentsCount;

        public Builder announcementDTOs(List<AnnouncementDTO> announcementDTOs) {
            this.announcementDTOs = announcementDTOs;
            return this;
        }

        public Builder enrollmentDTOs(List<EnrollmentDTO> enrollmentDTOs) {
            this.enrollmentDTOs = enrollmentDTOs;
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

        public Builder announcementsCount(int announcementsCount)
        {
            this.announcementsCount = announcementsCount;
            return this;
        }

        public Builder enrollmentsCount(int enrollmentsCount)
        {
            this.enrollmentsCount = enrollmentsCount;
            return this;
        }

        public CourseDTO build() {
            return new CourseDTO(announcementDTOs, enrollmentDTOs, courseMaterialDTOs, title, period, branche, announcementsCount, enrollmentsCount);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}