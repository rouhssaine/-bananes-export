package sh.aios.export.bananes.app.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sh.aios.export.bananes.app.dto.OrderDTO;
import sh.aios.export.bananes.app.dto.RecipientDTO;
import sh.aios.export.bananes.domain.model.Order;
import sh.aios.export.bananes.domain.model.Recipient;
import sh.aios.export.bananes.domain.port.OrderManagement;

@RestController
@RequestMapping(value = "/api/order")
public class OrderController {

	private final OrderManagement orderManagement;

	public OrderController(OrderManagement orderManagement) {
		this.orderManagement = orderManagement;
	}

	@GetMapping
	public List<OrderDTO> retrieve(final RecipientDTO recipient) {
		return orderManagement.consultOrders(dtoToRecipient(recipient)).stream()
				.map(this::orderToDto)
				.collect(Collectors.toList());
	}

	@PostMapping
	public void create(final RecipientDTO recipient,
			@RequestParam("deliveryDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate deliveryDate,
			@RequestParam final Integer quantity) {
		orderManagement.saveOrder(dtoToRecipient(recipient), deliveryDate, quantity);
	}

	@PutMapping
	public void update(@RequestBody final OrderDTO order) {
		orderManagement.updateOrder(dtoToOrder(order));
	}

	@DeleteMapping
	public void delete(@RequestBody final OrderDTO order) {
		orderManagement.removeOrder(dtoToOrder(order));
	}

	private OrderDTO orderToDto(Order order) {
		return new OrderDTO(order.getId(),
				recipientToDTO(order.getRecipient()),
				order.getDeliveryDate(),
				order.getBananasQuantity(),
				order.getPrice());
	}

	private RecipientDTO recipientToDTO(Recipient recipient) {
		return new RecipientDTO(recipient.getId(),
				recipient.getName(),
				recipient.getAddress(),
				recipient.getPostalCode(),
				recipient.getCity(),
				recipient.getState());
	}

	private Order dtoToOrder(OrderDTO dto) {
		return new Order(dto.getId(),
				dtoToRecipient(dto.getRecipient()),
				dto.getDeliveryDate(),
				dto.getBananasQuantity());
	}

	private Recipient dtoToRecipient(RecipientDTO dto) {
		return new Recipient(dto.getId(),
				dto.getName(),
				dto.getAddress(),
				dto.getPostalCode(),
				dto.getCity(),
				dto.getState());
	}
}
