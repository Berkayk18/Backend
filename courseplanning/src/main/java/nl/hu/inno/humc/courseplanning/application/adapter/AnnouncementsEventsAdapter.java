package nl.hu.inno.humc.courseplanning.application.adapter;

import nl.hu.inno.humc.courseplanning.application.port.AnnouncementsEventsConsumer;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class AnnouncementsEventsAdapter implements AnnouncementsEventsConsumer {
    @RabbitListener(queues = "announcements-queue")
    public void handleAnnouncementCreated(long announcementId) {
        System.out.println("Announcement created: " + announcementId);
    }
}
