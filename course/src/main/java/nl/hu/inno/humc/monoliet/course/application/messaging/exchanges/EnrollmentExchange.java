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
@Order(3)
public class EnrollmentExchange {
    public EnrollmentExchange()
    {
        this.createEnrollmentExchange();
    }

    private void createEnrollmentExchange() {
        var EXCHANGE = "enrollment-test";
        var enrollmentCreatedQueue = "enrollment-created-queue";
        var enrollmentDeletedQueue = "enrollment-deleted-queue";

        ConnectionFactory cf = new ConnectionFactory();

        try (Connection c = cf.newConnection()) {
            try (Channel channel = c.createChannel()) {
                channel.exchangeDeclare(EXCHANGE, BuiltinExchangeType.DIRECT);
                channel.queueDeclare(enrollmentCreatedQueue, false, false, false, null);
                channel.queueDeclare(enrollmentDeletedQueue, false, false, false, null);

                channel.addReturnListener(new LoggingReturnListener());

                channel.queueBind(enrollmentCreatedQueue, EXCHANGE, "enrollment.created");
                channel.queueBind(enrollmentDeletedQueue, EXCHANGE, "enrollment.deleted");

                channel.basicPublish(EXCHANGE, "enrollment.created", null, null);
                channel.basicPublish(EXCHANGE, "enrollment.deleted", null, null);
                channel.basicConsume(enrollmentCreatedQueue, true, new LoggingConsumer("enrollment created queue added"));
                channel.basicConsume(enrollmentDeletedQueue, true, new LoggingConsumer("enrollment deleted queue added"));

                System.out.println("Queues zijn succesvol aangemaakt.");
            }
        } catch (TimeoutException | IOException e) {
            e.printStackTrace();
        }
    }
}
