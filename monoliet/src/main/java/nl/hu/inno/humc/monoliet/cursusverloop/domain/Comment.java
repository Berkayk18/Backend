package nl.hu.inno.humc.monoliet.cursusverloop.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="COMMENT")
public class Comment {
    @GeneratedValue
    @Id
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="announcement_id", nullable = false)
    private Announcement announcement;

    private String content;
    private Long commenter;

    protected Comment(){}

    public Comment(String content, Long commenter, Announcement announcement){
        this.content = content;
        this.commenter = commenter;
        this.announcement = announcement;
    }

    public String getContent(){
        return this.content;
    }

    public Long getCommenter(){
        return this.commenter;
    }
}
