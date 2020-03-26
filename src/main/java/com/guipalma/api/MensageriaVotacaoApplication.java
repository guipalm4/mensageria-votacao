package com.guipalma.api;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication
@EnableAsync
public class MensageriaVotacaoApplication   implements ApplicationRunner {
	
	public static final String EXCHANGE_NAME = "appExchange";
    public static final String QUEUE_GENERIC_NAME = "appGenericQueue";
    public static final String QUEUE_SPECIFIC_NAME = "appResultadoQueue";
    public static final String ROUTING_KEY = "messages.key";
	
	public static void main(String[] args) {		
		SpringApplicationBuilder builder = new SpringApplicationBuilder(MensageriaVotacaoApplication.class);
		builder.headless(false);
		ConfigurableApplicationContext context = builder.run(args);
	}
	
	@Bean
    public TopicExchange appExchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue appQueueGeneric() {
        return new Queue(QUEUE_GENERIC_NAME);
    }

    @Bean
    public Queue appQueueSpecific() {
        return new Queue(QUEUE_SPECIFIC_NAME);
    }

    @Bean
    public Binding declareBindingGeneric() {
        return BindingBuilder.bind(appQueueGeneric()).to(appExchange()).with(ROUTING_KEY);
    }

    @Bean
    public Binding declareBindingSpecific() {
        return BindingBuilder.bind(appQueueSpecific()).to(appExchange()).with(ROUTING_KEY);
    }

 // You can comment the two methods below to use the default serialization / deserialization (instead of JSON)
    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        final var rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

	@Override
	public void run(ApplicationArguments args) throws Exception {
		// TODO Auto-generated method stub
	}	

}
