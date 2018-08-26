package com.rohat.service.order.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rohat.service.common.order.Order;
import com.rohat.service.order.repository.OrderRepository;

@RestController
public class OrderController {
	
	private Logger logger = LoggerFactory.getLogger(OrderController.class);
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Autowired
	private TopicExchange topicExchange;

	@PostMapping("/create")
	public Order create(@RequestBody Order order) {
		
		Order savedOrder = orderRepository.save(order);	
		logger.info("Event published for this order_id {}", savedOrder.getId());
		rabbitTemplate.convertAndSend(topicExchange.getName(), "order-created", savedOrder);
		
		return savedOrder;
	}
}
