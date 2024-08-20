package nl.hu.inno.humc.monoliet.course.application.messaging;

import nl.hu.inno.humc.monoliet.course.application.messaging.messages.CourseDeleteMessage;
import nl.hu.inno.humc.monoliet.course.application.messaging.messages.NewCourseMessage;
import nl.hu.inno.humc.monoliet.course.application.messaging.messages.UnexistingCourseMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class CourseMessaging {

    private final RabbitTemplate rabbitTemplate;

    public CourseMessaging(RabbitTemplate rabbitTemplate)
    {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendNewCourseMessage(NewCourseMessage message) {
        this.rabbitTemplate.convertAndSend("course-exchange", "course-added", message);
    }

    public void sendDeleteMessage(CourseDeleteMessage message) {

        this.rabbitTemplate.convertAndSend("course-exchange", "course-deleted", message);
    }

    public void courseDoesNotExistMessage(UnexistingCourseMessage message)
    {
        this.rabbitTemplate.convertAndSend("course-exchange", "unexisting-course", message);
    }
}
