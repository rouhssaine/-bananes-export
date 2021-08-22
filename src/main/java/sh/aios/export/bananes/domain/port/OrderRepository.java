package sh.aios.export.bananes.domain.port;

import java.util.List;

import sh.aios.export.bananes.domain.model.Order;

public interface OrderRepository {

	List<Order> getOrders();

	void saveOrder(Order order);

	void updateOrder(Order order);

	void deleteOrder(Order order);
}
