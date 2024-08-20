/*
package nl.hu.inno.humc.monoliet.cursusverloop.application;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import nl.hu.inno.humc.monoliet.cursusverloop.domain.Announcement;
import nl.hu.inno.humc.monoliet.cursusverloop.presentation.dto.AnnouncementDTO;

@SpringBootTest
class AnnouncementServiceTest {

    @Autowired
    private AnnouncementService announcementService;

    @Test
    @DisplayName("Test announcement creation and retrieval by cursus Long")
    void TestCreation() {
        announcementService.createAnnouncement(new AnnouncementDTO(123124251L, "Hello, World!!!", 80985693094723L));
        List<Announcement> announcements = announcementService.getAnnouncementByCursus(80985693094723L);
        assertEquals("Hello, World!!!", announcements.get(0).getContent());
    }


}
*/
