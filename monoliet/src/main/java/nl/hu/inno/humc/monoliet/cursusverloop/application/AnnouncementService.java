package nl.hu.inno.humc.monoliet.cursusverloop.application;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import nl.hu.inno.humc.monoliet.cursusverloop.domain.Announcement;
import nl.hu.inno.humc.monoliet.cursusverloop.domain.Comment;
import nl.hu.inno.humc.monoliet.cursusverloop.presentation.dto.AnnouncementDTO;
import nl.hu.inno.humc.monoliet.cursusverloop.presentation.dto.CommentDTO;
import nl.hu.inno.humc.monoliet.cursusverloop.repository.announcement.AnnouncementRepository;

@Service
public class AnnouncementService {

    AnnouncementRepository announcementRepository;

    public AnnouncementService(AnnouncementRepository announcementRepository){
        this.announcementRepository = announcementRepository;
    }

    @Transactional
    public void createAnnouncement(AnnouncementDTO announcementDTO){
        Announcement announcement = new Announcement(
            announcementDTO.getAnnouncer(),
            announcementDTO.getContent(),
            announcementDTO.getCursus()
        );
        announcementRepository.save(announcement);
    }

    @Transactional
    public void addComment(CommentDTO commentDTO){
        Optional<Announcement> optionalAnnouncement = announcementRepository.findById(commentDTO.getAnnouncement());
        if (optionalAnnouncement.isPresent()){
            Announcement announcement = optionalAnnouncement.get();
            Comment comment = new Comment(commentDTO.getContent(), commentDTO.getCommenter(), announcement);
            announcement.addComment(comment);
        }
        if (optionalAnnouncement.isEmpty()) {
            throw new NoSuchElementException();
        }
    }

    @Transactional
    public void removeAnnouncement(Long announcementId){
        this.announcementRepository.deleteById(announcementId);
    }

    @Transactional
    public void deleteComment(Long commentId, Long announcementId){
        Optional<Announcement> optionalAnnouncement = announcementRepository.findById(announcementId);
        if (optionalAnnouncement.isPresent()){
            Announcement announcement = optionalAnnouncement.get();
            announcement.deleteComment(commentId);
        }
        if (optionalAnnouncement.isEmpty()) {
            throw new NoSuchElementException();
        }
    }

    public List<Announcement> getAnnouncementByCursus(Long cursus){
        return announcementRepository.findAllByCursus(cursus);
    }
}
