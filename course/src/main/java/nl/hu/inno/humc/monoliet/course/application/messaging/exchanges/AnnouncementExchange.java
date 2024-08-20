package nl.hu.inno.humc.monoliet.course.application.messaging.exchanges;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import nl.hu.inno.humc.monoliet.course.application.messaging.logging.LoggingConsumer;
import nl.hu.inno.humc.monoliet.course.application.messaging.logging.LoggingReturnListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Component
@Order(2)
public class AnnouncementExchange {

    public AnnouncementExchange(){
        createAnnouncementExchange();
    }

    public void createAnnouncementExchange()
    {
        var EXCHANGE = "announcements";
        var announcementCreatedQueue = "announcement-created-queue";
        var announcementDeletedQueue = "announcement-deleted-queue";

        ConnectionFactory cf = new ConnectionFactory();

        try (Connection c = cf.newConnection()) {
            try (Channel channel = c.createChannel()) {
                channel.exchangeDeclare(EXCHANGE, BuiltinExchangeType.DIRECT);
                channel.queueDeclare(announcementCreatedQueue, false, false, false, null);
                channel.queueDeclare(announcementDeletedQueue, false, false, false, null);

                channel.addReturnListener(new LoggingReturnListener());

                channel.queueBind(announcementCreatedQueue, EXCHANGE, "announcement.created");
                channel.queueBind(announcementDeletedQueue, EXCHANGE, "announcement.deleted");

                channel.basicPublish(EXCHANGE, "announcement.created", null, null);
                channel.basicPublish(EXCHANGE, "announcement.deleted", null, null);
                channel.basicConsume(announcementCreatedQueue, true, new LoggingConsumer("announcement created queue added"));
                channel.basicConsume(announcementDeletedQueue, true, new LoggingConsumer("announcement deleted queue added"));

                System.out.println("Queues zijn succesvol aangemaakt.");
            }
        } catch (TimeoutException | IOException e) {
            e.printStackTrace();
        }
    }
}
