package sh.aios.export.bananes.infra.adapter;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import sh.aios.export.bananes.domain.model.Order;
import sh.aios.export.bananes.domain.model.Recipient;
import sh.aios.export.bananes.domain.port.OrderRepository;
import sh.aios.export.bananes.infra.entity.BananaOrder;
import sh.aios.export.bananes.infra.entity.Customer;
import sh.aios.export.bananes.infra.repository.OrderJpaRepository;

@Service
public class OrderRepositoryAdapter implements OrderRepository {

	private final OrderJpaRepository orderJpaRepository;

	public OrderRepositoryAdapter(OrderJpaRepository orderJpaRepository) {
		this.orderJpaRepository = orderJpaRepository;
	}

	@Override
	public List<Order> getOrders() {
		return StreamSupport.stream(orderJpaRepository.findAll().spliterator(), false)
				.map(this::dtoOrderToOrder)
				.collect(Collectors.toList());
	}

	@Override
	public void saveOrder(Order order) {
		orderJpaRepository.save(orderToDtoOrder(order));
	}

	@Override
	public void updateOrder(Order order) {
		orderJpaRepository.save(orderToDtoOrder(order));
	}

	@Override
	public void deleteOrder(Order order) {
		orderJpaRepository.delete(orderToDtoOrder(order));
	}

	private BananaOrder orderToDtoOrder(Order order) {
		var dto = new BananaOrder();
		dto.setId(order.getId());
		dto.setCustomer(recipientToCustomer(order.getRecipient()));
		dto.setDeliveryDate(localDateToDate(order.getDeliveryDate()));
		dto.setQuantity(order.getBananasQuantity());
		return dto;
	}

	private Customer recipientToCustomer(Recipient recipient) {
		var customer = new Customer();
		customer.setId(recipient.getId());
		customer.setName(recipient.getName());
		customer.setAddress(recipient.getAddress());
		customer.setCity(recipient.getCity());
		customer.setPostalCode(recipient.getPostalCode());
		customer.setState(recipient.getState());
		return customer;
	}

	private Date localDateToDate(LocalDate localDate) {
		return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}

	private Order dtoOrderToOrder(BananaOrder order) {
		return new Order(order.getId(),
				customerToRecipient(order.getCustomer()),
				dateToLocalDate(order.getDeliveryDate()),
				order.getQuantity());
	}

	private Recipient customerToRecipient(Customer customer) {
		return new Recipient(customer.getId(),
				customer.getName(),
				customer.getAddress(),
				customer.getPostalCode(),
				customer.getCity(),
				customer.getState());
	}

	private LocalDate dateToLocalDate(Date date) {
		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}
}
