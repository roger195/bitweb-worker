package bitweb.wordcloud.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitMQConfig {

    @Value("${rabbitmq.queue.words}")
    private String queue;
    @Value("${rabbitmq.exchange.words}")
    private String exchange;
    @Value("${rabbitmq.routing.key.words}")
    private String routingKey;
    @Value("${rabbitmq.dl.exchange.words}")
    private String deadLetterExchange;
    @Value("${rabbitmq.dl.queue.words}")
    private String deadLetterQueue;

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(exchange);
    }

    @Bean
    public DirectExchange deadLetterExchange() {
        return new DirectExchange(deadLetterExchange);
    }

    @Bean
    Queue queue() {
        return QueueBuilder.durable(queue)
                .withArgument("x-dead-letter-exchange", deadLetterExchange)
                .withArgument("x-dead-letter-routing-key", deadLetterQueue)
                .build();
    }

    @Bean
    Queue deadLetterQueue() {
        return new Queue(deadLetterQueue);
    }

    @Bean
    public Binding deadLetterBinding(){
        return BindingBuilder
                .bind(deadLetterQueue())
                .to(deadLetterExchange()).withQueueName();
    }

    @Bean
    public Binding binding(){
        return BindingBuilder
                .bind(queue())
                .to(exchange())
                .with(routingKey);
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
