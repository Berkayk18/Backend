package nl.hu.inno.humc.courseplanning.application.adapter;

import nl.hu.inno.humc.courseplanning.application.port.CourseServicePort;
import org.springframework.stereotype.Component;

@Component
public class FakeCourseServiceAdapter implements CourseServicePort {
    @Override
    public boolean doesCourseExist(Long courseId) {
        return true;
    }
}
