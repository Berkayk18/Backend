package nl.hu.inno.humc.monoliet.cursusverloop.presentation.dto;

public class AnnouncementDTO {
    private Long announcer;
    private String content;
    private Long cursus;

    protected AnnouncementDTO(){}

    public AnnouncementDTO(Long announcer, String content, Long cursus){
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
}
