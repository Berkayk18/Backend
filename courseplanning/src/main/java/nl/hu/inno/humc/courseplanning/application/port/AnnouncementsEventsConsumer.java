package nl.hu.inno.humc.courseplanning.application.port;

public interface AnnouncementsEventsConsumer {
    void handleAnnouncementCreated(long AnnouncementId);
}
