package nl.hu.inno.humc.monoliet.course.application.messaging.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Bean
    public DirectExchange pubsubCourseExchange() {
        return new DirectExchange("course-exchange");
    }

    @Bean
    public Queue courseQueue() {
        return new Queue("course-queues");
    }

    @Bean
    public Binding binding1(DirectExchange exchange, Queue queue) {
        return BindingBuilder.bind(queue)
                .to(exchange)
                .with("course-added");
    }

    @Bean
    public Binding binding2(DirectExchange exchange, Queue queue) {
        return BindingBuilder.bind(queue)
                .to(exchange)
                .with("course-deleted");
    }

    @Bean
    public Binding binding3(DirectExchange exchange, Queue queue) {
        return BindingBuilder.bind(queue)
                .to(exchange)
                .with("unexisting-course");
    }

    @Bean
    MessageConverter getConverter(){
        return new Jackson2JsonMessageConverter();
    }
}