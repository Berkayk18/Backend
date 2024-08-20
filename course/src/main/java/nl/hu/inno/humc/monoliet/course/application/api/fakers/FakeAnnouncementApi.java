package nl.hu.inno.humc.monoliet.course.application.api.fakers;

import nl.hu.inno.humc.monoliet.course.application.api.interfaces.IAnnouncement;
import nl.hu.inno.humc.monoliet.course.application.dto.AnnouncementDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FakeAnnouncementApi implements IAnnouncement {
    @Override
    public List<AnnouncementDTO> getAnnouncementPerCourse(Long courseId) {
        return List.of(new AnnouncementDTO("Canvas",
                "There are no announcement for this course"));
    }
}
