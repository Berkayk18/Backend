package nl.hu.inno.humc.monoliet.course.repository;

import nl.hu.inno.humc.monoliet.course.domain.Course;
import nl.hu.inno.humc.monoliet.course.domain.enums.Period;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {
    Optional<Course> findCourseByCourseDetail_Title(String title);

    void deleteCourseByCourseDetail_Title(String title);

    boolean existsByCourseDetail_Title(String title);

    @Query(value = "SELECT c.courseDetail.branche , COUNT(c.id) FROM Course c GROUP BY c.courseDetail.branche")
    List<Object[]> countAllByCourseDetail_Branche();

    List<Object[]> countAllByCourseDetail_Period(Period courseDetail_period) ;
}
