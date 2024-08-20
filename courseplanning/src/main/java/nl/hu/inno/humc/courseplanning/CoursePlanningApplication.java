package nl.hu.inno.humc.courseplanning;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class CoursePlanningApplication {
    public static void main(String[] args) {
        SpringApplication.run(CoursePlanningApplication.class, args);
        System.out.println("Course Planning Application started!");
    }
}
