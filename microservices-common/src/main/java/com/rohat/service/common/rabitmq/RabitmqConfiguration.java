package com.rohat.service.common.rabitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;

@Configuration
public class RabitmqConfiguration {

	@Value("${subscriber.queue}")
	private String queueName;

	@Value("${subscriber.routingKey}")
	private String routingKey;

	@Bean
	public TopicExchange receiverExchange() {
		return new TopicExchange("eventExchange");
	}

	@Bean
	public Queue eventReceivingQueue() {
		Assert.notNull(queueName, "routing key is require");

		return new Queue(queueName);
	}

	@Bean
	public Binding binding(Queue eventReceivingQueue, TopicExchange receiverExchange) {
		Assert.notNull(routingKey, "routing key is require");
		
		return BindingBuilder.bind(eventReceivingQueue).to(receiverExchange).with(routingKey);
	}

}