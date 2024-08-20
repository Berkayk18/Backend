package nl.hu.inno.humc.monoliet.course.application.dto;

public record AnnouncementDTO(String announcer, String content) {
    public static class Builder {
        private String announcer;
        private String content;

        public Builder setAnnouncer(String announcer) {
            this.announcer = announcer;
            return this;
        }

        public Builder setContent(String content) {
            this.content = content;
            return this;
        }

        public AnnouncementDTO build() {
            return new AnnouncementDTO(announcer, content);
        }
    }
}
