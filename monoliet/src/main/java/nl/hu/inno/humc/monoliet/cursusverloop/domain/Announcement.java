package nl.hu.inno.humc.monoliet.cursusverloop.domain;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "ANNOUNCEMENT")
public class Announcement {
    @Id
    @GeneratedValue
    private Long id;

    private Long announcer;

    private String content;

    private Long cursus;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "announcement", cascade = CascadeType.ALL)
    private List<Comment> comments;

    protected Announcement(){}

    public Announcement(Long announcer, String content, Long cursus){
        this.announcer = announcer;
        this.content = content;
        this.cursus = cursus;
    }

    public String getContent(){
        return this.content;
    }

    public Long getAnnouncer(){
        return this.announcer;
    }

    public Long getCursus(){
        return this.cursus;
    }

    public Long getId(){
        return this.id;
    }

    public List<Comment> getComments(){
        return this.comments;
    }

    public void addComment(Comment comment){
        this.comments.add(comment);
    }

    public void deleteComment(Long commentId){
        // this.comments.remove(commentId);
        // Currently does nothing
    }
}