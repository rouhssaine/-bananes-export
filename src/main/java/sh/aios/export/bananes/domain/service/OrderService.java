package sh.aios.export.bananes.domain.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.google.common.base.Preconditions;

import sh.aios.export.bananes.domain.model.Order;
import sh.aios.export.bananes.domain.model.Recipient;
import sh.aios.export.bananes.domain.port.OrderManagement;
import sh.aios.export.bananes.domain.port.OrderRepository;

public class OrderService implements OrderManagement {

	private static final String DELIVERY_DATE_ERROR_MESSAGE = "La date de livraison doit être, au minimum, une semaine dans le futur par rapport à la date du jour.";

	private static final String QUANTITY_ERROR_MESSAGE = "La quantité de bananes demandé doit être compris entre 0 et 10 000 et doit être un multiple de 25 (parce qu’une caisse de bananes contient 25kg).";

	private final OrderRepository orderRepository;

	public OrderService(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}

	@Override
	public List<Order> consultOrders(Recipient recipient) {
		return orderRepository.getOrders().stream()
				.filter(order -> recipient.equals(order.getRecipient()))
				.collect(Collectors.toList());
	}

	@Override
	public void saveOrder(Recipient recipient, LocalDate deliveryDate, Integer quantity) {
		preconditions(deliveryDate, quantity);
		var order = new Order(recipient, deliveryDate, quantity);
		orderRepository.saveOrder(order);
	}

	@Override
	public void updateOrder(Order order) {
		preconditions(order.getDeliveryDate(), order.getBananasQuantity());
		orderRepository.updateOrder(order);
	}

	@Override
	public void removeOrder(Order order) {
		orderRepository.deleteOrder(order);
	}

	private void preconditions(LocalDate deliveryDate, Integer quantity) {
		Preconditions.checkArgument(hasValidDeliveryDate(deliveryDate),
				DELIVERY_DATE_ERROR_MESSAGE, deliveryDate);

		Preconditions.checkArgument(hasValidQuantity(quantity),
				QUANTITY_ERROR_MESSAGE, quantity);
	}

	private boolean hasValidDeliveryDate(LocalDate deliveryDate) {
		LocalDate futurDate = LocalDate.now().plusWeeks(1);
		return futurDate.isEqual(deliveryDate) || futurDate.isBefore(deliveryDate);
	}

	private boolean hasValidQuantity(Integer quantity) {
		return quantity > 0 && quantity < 10000 && quantity % 25 == 0;
	}

}
