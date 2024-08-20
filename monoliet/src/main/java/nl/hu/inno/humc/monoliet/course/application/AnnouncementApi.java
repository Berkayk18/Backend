package nl.hu.inno.humc.monoliet.course.application;

import nl.hu.inno.humc.monoliet.course.application.dto.AnnouncementDTO;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Component
public class AnnouncementApi {

    private final RestTemplate restTemplate;

    public AnnouncementApi(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<AnnouncementDTO> getAnnouncementByCursus(Long cursusId) {
        String url = "https://voorbeeld.com/api/announcements?cursus=" + cursusId;

        try {
            ResponseEntity<List<AnnouncementDTO>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {
                    }
            );

            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            } else {
                return Collections.emptyList();
            }
        } catch (RestClientException e) {
            return getFallbackAnnouncements();
        }
    }

    public List<AnnouncementDTO> getFallbackAnnouncements() {
        return Collections.emptyList();
    }
}
