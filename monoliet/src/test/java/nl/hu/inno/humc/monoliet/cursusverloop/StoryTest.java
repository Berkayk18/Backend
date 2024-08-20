/*
package nl.hu.inno.humc.monoliet.cursusverloop;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import nl.hu.inno.humc.monoliet.cursusverloop.application.AnnouncementService;
import nl.hu.inno.humc.monoliet.cursusverloop.domain.Announcement;
import nl.hu.inno.humc.monoliet.cursusverloop.presentation.AnnouncementController;
import nl.hu.inno.humc.monoliet.cursusverloop.presentation.dto.AnnouncementDTO;
import nl.hu.inno.humc.monoliet.cursusverloop.presentation.dto.CommentDTO;

@SpringBootTest
class StoryTest {
    @Autowired
    private AnnouncementService announcementService;
    @Test
    @DisplayName("Announcement test user story")
    void announcementCaseTest(){
        AnnouncementDTO announcementDTO = new AnnouncementDTO(275228915461L, "Hello, World!!!", 8378286172461L);

        AnnouncementController controller = new AnnouncementController(announcementService);

        controller.createAnnouncement(announcementDTO);
        List<Announcement> announcements = controller.getAllAnnouncementsByCursus(8378286172461L);
        assertEquals(1, announcements.size());
        
        CommentDTO commentDTO = new CommentDTO(132123123L, "Flat earth", announcements.get(0).getId());
        assertEquals(announcements.get(0).getId(), commentDTO.getAnnouncement());
        
        controller.createComment(commentDTO);
        announcements = controller.getAllAnnouncementsByCursus(8378286172461L);
        assertEquals(1, announcements.get(0).getComments().size());
    }
}
*/
