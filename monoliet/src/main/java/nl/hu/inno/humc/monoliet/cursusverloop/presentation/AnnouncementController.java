package nl.hu.inno.humc.monoliet.cursusverloop.presentation;

import org.springframework.web.bind.annotation.RestController;

import nl.hu.inno.humc.monoliet.cursusverloop.application.AnnouncementService;
import nl.hu.inno.humc.monoliet.cursusverloop.domain.Announcement;
import nl.hu.inno.humc.monoliet.cursusverloop.presentation.dto.AnnouncementDTO;
import nl.hu.inno.humc.monoliet.cursusverloop.presentation.dto.CommentDTO;

import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;




@RestController
@RequestMapping("/announcement")
public class AnnouncementController {
    private AnnouncementService announcementService;

    public AnnouncementController(AnnouncementService announcementService){
        this.announcementService = announcementService;
    }
    
    @PostMapping(value = "/create", consumes = "application/json")
    public ResponseEntity createAnnouncement(@RequestBody AnnouncementDTO announcement) {

        this.announcementService.createAnnouncement(announcement);
        
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/comment", consumes = "application/json")
    @ResponseBody
    public ResponseEntity createComment(@RequestBody CommentDTO comment) {
        try {
            this.announcementService.addComment(comment);    
            return ResponseEntity.ok().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No such cursus exists!");
        }
    }
    
    @GetMapping(value = "/", consumes = "application/json")
    public List<Announcement> getAllAnnouncementsByCursus(@RequestParam Long cursus) {
        return this.announcementService.getAnnouncementByCursus(cursus);
    }  

    @DeleteMapping(value = "/delete", consumes = "application/json")
    public ResponseEntity deleteAnnouncement(@RequestParam Long announcement) {
        this.announcementService.removeAnnouncement(announcement);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/comment", consumes = "application/json")
    public ResponseEntity deleteComment(@RequestParam Long comment, @RequestParam Long announcement) {
        this.announcementService.deleteComment(comment, announcement);
        return ResponseEntity.ok().build();
    }
}
