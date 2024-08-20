package nl.hu.inno.humc.monoliet.course.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity(name = "CourseAnnouncement")
@Table(name = "announcement")
public class Announcement {
    @Id
    @GeneratedValue
    private Long id;

    private Long announcer;

    private String content;

    protected Announcement(){}

    public Announcement(Long announcer, String content){
        this.announcer = announcer;
        this.content = content;
    }

    public String getContent(){
        return this.content;
    }

    public Long getAnnouncer(){
        return this.announcer;
    }

    public Long getId(){
        return this.id;
    }

}