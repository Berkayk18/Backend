package nl.hu.inno.humc.courseplanning.application;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

//  A topic exchange seems more appropriate, since we have events that inform about creation as wel as deletion

    @Bean
    public FanoutExchange enrollmentsExchange() {
        return new FanoutExchange("enrollments");
    }

    @Bean
    public FanoutExchange courseExchange() {
        return new FanoutExchange("courses");
    }

    @Bean
    public FanoutExchange announcementsExchange() {
        return new FanoutExchange("announcements", false, false);
    }

    @Bean
    public Queue coursesQueue() {
        return new Queue("courses-queue");
    }

    @Bean
    public Queue announcementsQueue() {
        return new Queue("announcements-queue");
    }

    @Bean
    public Binding courseBinding(Queue coursesQueue, FanoutExchange courseExchange) {
        return BindingBuilder.bind(coursesQueue).to(courseExchange);
    }

    @Bean
    public Binding announcementsBinding(Queue announcementsQueue, FanoutExchange announcementsExchange) {
        return BindingBuilder.bind(announcementsQueue).to(announcementsExchange);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory, Jackson2JsonMessageConverter jackson2JsonMessageConverter) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(jackson2JsonMessageConverter);
        return factory;
    }

    @Bean
    public Queue TestEnrollmentsQueue() {
        return new Queue("test-enrollments-queue");
    }

    @Bean
    public Binding TestEnrollmentsBinding(Queue TestEnrollmentsQueue, FanoutExchange enrollmentsExchange) {
        return BindingBuilder.bind(TestEnrollmentsQueue).to(enrollmentsExchange);
    }
}
