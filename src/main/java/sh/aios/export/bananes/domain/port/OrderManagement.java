package sh.aios.export.bananes.domain.port;

import java.time.LocalDate;
import java.util.List;

import sh.aios.export.bananes.domain.model.Order;
import sh.aios.export.bananes.domain.model.Recipient;

public interface OrderManagement {

	List<Order> consultOrders(Recipient recipient);

	void saveOrder(Recipient recipient, LocalDate deliveryDate, Integer quantity);

	void updateOrder(Order order);

	void removeOrder(Order order);
}
