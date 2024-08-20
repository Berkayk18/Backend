package nl.hu.inno.humc.monoliet.cursusverloop.presentation.dto;

public class CommentDTO {
    private Long commenter;
    private String content;
    private Long announcement;

    protected CommentDTO(){}

    public CommentDTO(Long commenter, String content, Long announcement){
        this.commenter = commenter;
        this.content = content;
        this.announcement = announcement;
    }

    public String getContent(){
        return this.content;
    }

    public Long getAnnouncement(){
        return this.announcement;
    }

    public Long getCommenter(){
        return this.commenter;
    }
}
