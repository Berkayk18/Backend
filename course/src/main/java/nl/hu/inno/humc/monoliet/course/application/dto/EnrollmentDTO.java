package nl.hu.inno.humc.monoliet.course.application.dto;

public record EnrollmentDTO(String username, String type) {
    public static class Builder
    {
        private String username;
        private String type;

        public Builder setUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder setType(String type) {
            this.type = type;
            return this;
        }

        public EnrollmentDTO build() {
            return new EnrollmentDTO(username, type);
        }
    }
}
