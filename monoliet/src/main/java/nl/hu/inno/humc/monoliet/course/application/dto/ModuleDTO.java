package nl.hu.inno.humc.monoliet.course.application.dto;

import java.util.Date;

public record ModuleDTO(String name, Date deadline) {
    public static class Builder {
        private String name;
        private Date deadline;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder deadline(Date deadline) {
            this.deadline = deadline;
            return this;
        }

        public ModuleDTO build() {
            return new ModuleDTO(name, deadline);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}