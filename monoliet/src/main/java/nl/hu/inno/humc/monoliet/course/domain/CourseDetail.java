package nl.hu.inno.humc.monoliet.course.domain;

import jakarta.persistence.*;
import nl.hu.inno.humc.monoliet.course.domain.enums.Branche;
import nl.hu.inno.humc.monoliet.course.domain.enums.Period;

import java.util.Date;

@Embeddable
public class CourseDetail {
    private String title;

    @Enumerated(EnumType.STRING)
    private Period period;

    @Enumerated(EnumType.STRING)
    private Branche branche;

    @Temporal(TemporalType.DATE)
    private Date createDate;

    public CourseDetail(String title, String period, String branche) {
        setTitle(title);
        setPeriod(period);
        setBranche(branche);
        setCreateDate();
    }

    protected CourseDetail() {
    }

    // Getters
    public String getTitle() {
        return title;
    }

    public Period getPeriod() {
        return period;
    }

    public Branche getBranche() {
        return branche;
    }

    public Date getCreateDate() {
        return createDate;
    }

    // Setters with validation
    protected void setTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }
        if (title.length() > 100) {
            throw new IllegalArgumentException("Title cannot be longer than 100 characters");
        }
        this.title = title;
    }

    protected void setPeriod(String period) {
        if (period == null || period.trim().isEmpty()) {
            throw new IllegalArgumentException("Period cannot be empty");
        }

        period = period.toUpperCase();

        try {
            this.period = Period.valueOf(period);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid period value");
        }
    }

    protected void setBranche(String branche) {
        if (branche == null || branche.trim().isEmpty()) {
            throw new IllegalArgumentException("Branche cannot be empty");
        }

        branche = branche.toUpperCase();

        try {
            this.branche = Branche.valueOf(branche);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid branche value");
        }
    }


    protected void setCreateDate() {
        this.createDate = new Date();
    }
}
