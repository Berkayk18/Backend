package nl.hu.inno.humc.monoliet.course.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Announcement {
    @Id
    @GeneratedValue
    private Long id;

    private String announcer;

    private String content;

    public Announcement(String announcer, String content){
        this.announcer = announcer;
        this.content = content;
    }

    protected Announcement(){}

    public String getContent(){
        return this.content;
    }

    public String getAnnouncer(){
        return this.announcer;
    }

    public Long getId(){
        return this.id;
    }

}