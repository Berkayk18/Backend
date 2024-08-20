package nl.hu.inno.humc.courseplanning.application.adapter;

import nl.hu.inno.humc.courseplanning.application.port.CourseServicePort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CourseServiceAdapter implements CourseServicePort {

    private final RestTemplate restTemplate;
    private final String courseServiceUrl;

    public CourseServiceAdapter(RestTemplate restTemplate, @Value("${course.service.url}") String courseServiceUrl) {
        this.restTemplate = restTemplate;
        this.courseServiceUrl = courseServiceUrl;
    }

    @Override
    @Cacheable(value = "courseExistsCache", key = "#courseId", unless = "#result == false")
    public boolean doesCourseExist(Long courseId) {
        try {
            return Boolean.TRUE.equals(
                    restTemplate.getForObject(courseServiceUrl + "/course/{courseId}/exists", Boolean.class, courseId));
        } catch (Exception e) {
            return false;
        }
    }
}
