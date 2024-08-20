package nl.hu.inno.humc.courseplanning.application.adapter;

import nl.hu.inno.humc.courseplanning.application.dto.NewStudentDTO;
import nl.hu.inno.humc.courseplanning.application.dto.RegisterStudentDTO;
import nl.hu.inno.humc.courseplanning.application.port.OsirisEventsConsumer;
import nl.hu.inno.humc.courseplanning.application.service.EnrollmentService;
import nl.hu.inno.humc.courseplanning.application.service.UserService;
import nl.hu.inno.humc.courseplanning.domain.Email;
import nl.hu.inno.humc.courseplanning.domain.Enrollment;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import java.util.Random;

public class OsirisEventsAdapter implements OsirisEventsConsumer {

    private final UserService userService;
    private final EnrollmentService enrollmentService;

    public OsirisEventsAdapter(UserService userService, EnrollmentService enrollmentService) {
        this.userService = userService;
        this.enrollmentService = enrollmentService;
    }

    @Override
    @RabbitListener(queues = "new-student-queue")
    public void receiveNewStudent(NewStudentDTO newStudentDTO) {
        userService.addUser(newStudentDTO.getId(), newStudentDTO.getName(), new Email(newStudentDTO.getEmailAddress()));
    }

    @Override
    @RabbitListener(queues = "student-register-queue")
    public void receiveStudentRegister(RegisterStudentDTO registerStudentDTO) {
        // if I somehow know which study belongs to which course I can actually input them into the right course
        // registerStudentDTO.getStudyId();
        // for now I will just enroll them in a course
        Random random = new Random();
        long randomLong = random.nextLong();

        enrollmentService.enrollUserToCourse(randomLong, registerStudentDTO.getId(), Enrollment.Type.STUDENT);
    }

    @Override
    @RabbitListener(queues = "student-deregister-queue")
    public void receiveStudentDeregister(RegisterStudentDTO registerStudentDTO) {
        System.out.println("Received student deregistration: " + registerStudentDTO);
    }
}
