package nl.hu.inno.humc.monoliet.course.domain;

import java.util.Date;

public class ModuleContent {

    private String name;

    private Date deadline;

    public ModuleContent(String name, Date deadline) {
        this.validateName(name);
        this.validateDeadline(deadline);
    }

    protected ModuleContent() {};

    private void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        if (name.length() > 100) {
            throw new IllegalArgumentException("Name cannot be longer than 100 characters");
        }
        this.name = name;
    }

    private void validateDeadline(Date deadline) {
        if (deadline == null) {
            throw new IllegalArgumentException("Deadline is required");
        }

        Date currentDate = new Date();
        if (deadline.before(currentDate)) {
            throw new IllegalArgumentException("Deadline cannot be in the past");
        }

        this.deadline = deadline;
    }

    public String getName() {
        return name;
    }

    protected void setName(String name) {
        this.name = name;
    }

    public Date getDeadline() {
        return deadline;
    }

    protected void setDeadline(Date deadline) {
        this.deadline = deadline;
    }
}
