package sh.aios.export.bananes.infra.adapter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import sh.aios.export.bananes.domain.model.Order;
import sh.aios.export.bananes.domain.model.Recipient;
import sh.aios.export.bananes.infra.entity.BananaOrder;
import sh.aios.export.bananes.infra.entity.Customer;
import sh.aios.export.bananes.infra.repository.OrderJpaRepository;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class OrderRepositoryAdapterTest {

	@InjectMocks
	private OrderRepositoryAdapter adapter;

	@Mock
	private OrderJpaRepository repository;

	@Captor
	private ArgumentCaptor<BananaOrder> captor;

	@Test
	void should_return_all_orders() {
		//Given
		var order = createOrder();
		when(repository.findAll()).thenReturn(List.of(order));

		//When
		var result = adapter.getOrders();

		//Then
		assertThat(result).hasSize(1);
	}

	@Test
	void should_save_order() {
		//Given
		var toto = new Recipient(1, "Toto", "5 avenue Anatole France", "75007", "Paris", "France");
		var order = new Order(1, toto, LocalDate.now(), 50);

		//When
		adapter.saveOrder(order);

		//Then
		verify(repository).save(captor.capture());
		assertThat(captor.getValue().getCustomer().getName()).isEqualTo("Toto");
		assertThat(captor.getValue().getQuantity()).isEqualTo(50);
	}

	@Test
	void should_update_order() {
		//Given
		var toto = new Recipient(1, "Toto", "5 avenue Anatole France", "75007", "Paris", "France");
		var order = new Order(1, toto, LocalDate.now(), 50);

		//When
		adapter.updateOrder(order);

		//Then
		verify(repository).save(captor.capture());
		assertThat(captor.getValue().getCustomer().getName()).isEqualTo("Toto");
		assertThat(captor.getValue().getQuantity()).isEqualTo(50);
	}

	@Test
	void should_delete_order() {
		//Given
		var toto = new Recipient(1, "Toto", "5 avenue Anatole France", "75007", "Paris", "France");
		var order = new Order(1, toto, LocalDate.now(), 50);

		//When
		adapter.deleteOrder(order);

		//Then
		verify(repository).delete(captor.capture());
		assertThat(captor.getValue().getCustomer().getName()).isEqualTo("Toto");
		assertThat(captor.getValue().getQuantity()).isEqualTo(50);
	}

	private BananaOrder createOrder() {
		BananaOrder order = new BananaOrder();
		order.setId(1);
		order.setCustomer(new Customer());
		order.setDeliveryDate(new Date());
		order.setQuantity(50);
		return order;
	}
}
