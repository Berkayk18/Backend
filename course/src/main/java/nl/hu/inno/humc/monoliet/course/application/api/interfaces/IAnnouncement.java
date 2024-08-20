package nl.hu.inno.humc.monoliet.course.application.api.interfaces;

import nl.hu.inno.humc.monoliet.course.application.dto.AnnouncementDTO;

import java.util.List;

public interface IAnnouncement {
    List<AnnouncementDTO> getAnnouncementPerCourse(Long courseId);
}
