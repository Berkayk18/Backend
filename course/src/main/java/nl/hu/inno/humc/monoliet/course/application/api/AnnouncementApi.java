package nl.hu.inno.humc.monoliet.course.application.api;

import nl.hu.inno.humc.monoliet.course.application.api.interfaces.IAnnouncement;
import nl.hu.inno.humc.monoliet.course.application.dto.AnnouncementDTO;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Component
public class AnnouncementApi implements IAnnouncement {
    private final RestTemplate restTemplate;

    public AnnouncementApi(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<AnnouncementDTO> getAnnouncementPerCourse(Long courseId) {
        String url = "http://localhost:8083/announcement/{courseId}";
        try {
            ResponseEntity<List<AnnouncementDTO>> responseEntity = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {
                    },
                    courseId
            );

            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                return responseEntity.getBody();
            } else {
                return Collections.emptyList();
            }
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }
}
