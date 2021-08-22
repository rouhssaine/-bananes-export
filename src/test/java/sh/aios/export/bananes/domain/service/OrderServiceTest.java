package sh.aios.export.bananes.domain.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
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
import sh.aios.export.bananes.domain.port.OrderRepository;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class OrderServiceTest {

	@InjectMocks
	private OrderService service;

	@Mock
	private OrderRepository repository;

	@Captor
	private ArgumentCaptor<Order> captor;

	@Test
	void should_return_all_orders_of_recipient_when_consult_orders() {
		//Given
		var toto = new Recipient(1, "Toto", "5 avenue Anatole France", "75007", "Paris", "France");
		var titi = new Recipient(1, "Titi", "5 avenue des Champs-Elysées", "75008", "Paris", "France");
		var totoOrder1 = new Order(1, toto, LocalDate.now(), 50);
		var totoOrder2 = new Order(1, toto, LocalDate.now(), 100);
		var titiOrder = new Order(1, titi, LocalDate.now(), 50);
		var orders = List.of(totoOrder1, totoOrder2, titiOrder);
		when(repository.getOrders()).thenReturn(orders);

		//When
		List<Order> result = service.consultOrders(toto);

		//Then
		assertThat(result).hasSize(2).containsExactlyInAnyOrder(totoOrder1, totoOrder2);
	}

	@Test
	void should_not_save_order_when_delivery_date_less_than_a_week() {
		//Given
		var toto = new Recipient(1, "Toto", "5 avenue Anatole France", "75007", "Paris", "France");
		var deliveryDate = LocalDate.now().plusDays(5);
		var quantity = 50;

		//When & Then
		assertThatThrownBy(() -> service.saveOrder(toto, deliveryDate, quantity))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("La date de livraison doit être, au minimum, une semaine dans le futur par rapport à la date du jour. [%s]", deliveryDate)
				.hasNoCause();
	}

	@Test
	void should_not_save_order_when_quantity_is_zero() {
		//Given
		var toto = new Recipient(1, "Toto", "5 avenue Anatole France", "75007", "Paris", "France");
		var deliveryDate = LocalDate.now().plusMonths(1);
		var quantity = 0;

		//When & Then
		assertThatThrownBy(() -> service.saveOrder(toto, deliveryDate, quantity))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage(
						"La quantité de bananes demandé doit être compris entre 0 et 10 000 et doit être un multiple de 25 (parce qu’une caisse de bananes contient 25kg). [%s]",
						quantity)
				.hasNoCause();
	}

	@Test
	void should_not_save_order_when_quantity_is_greater_than_ten_thousand() {
		//Given
		var toto = new Recipient(1, "Toto", "5 avenue Anatole France", "75007", "Paris", "France");
		var deliveryDate = LocalDate.now().plusMonths(1);
		var quantity = 15000;

		//When & Then
		assertThatThrownBy(() -> service.saveOrder(toto, deliveryDate, quantity))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage(
						"La quantité de bananes demandé doit être compris entre 0 et 10 000 et doit être un multiple de 25 (parce qu’une caisse de bananes contient 25kg). [%s]",
						quantity)
				.hasNoCause();
	}

	@Test
	void should_not_save_order_when_quantity_is_not_multiple_of_twenty_five() {
		//Given
		var toto = new Recipient(1, "Toto", "5 avenue Anatole France", "75007", "Paris", "France");
		var deliveryDate = LocalDate.now().plusMonths(1);
		var quantity = 10;

		//When & Then
		assertThatThrownBy(() -> service.saveOrder(toto, deliveryDate, quantity))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage(
						"La quantité de bananes demandé doit être compris entre 0 et 10 000 et doit être un multiple de 25 (parce qu’une caisse de bananes contient 25kg). [%s]",
						quantity)
				.hasNoCause();
	}

	@Test
	void should_save_order_when_delivery_date_is_one_week_from_current_date_and_valid_quantity() {
		//Given
		var toto = new Recipient(1, "Toto", "5 avenue Anatole France", "75007", "Paris", "France");
		var deliveryDate = LocalDate.now().plusWeeks(1);
		var quantity = 50;

		//When
		service.saveOrder(toto, deliveryDate, quantity);

		//Then
		verify(repository).saveOrder(captor.capture());
		assertThat(captor.getValue().getRecipient()).isEqualTo(toto);
		assertThat(captor.getValue().getDeliveryDate()).isEqualTo(deliveryDate);
		assertThat(captor.getValue().getBananasQuantity()).isEqualTo(quantity);
	}

	@Test
	void should_save_order_when_delivery_date_one_week_after_current_date_and_valid_quantity() {
		//Given
		var toto = new Recipient(1, "Toto", "5 avenue Anatole France", "75007", "Paris", "France");
		var deliveryDate = LocalDate.now().plusWeeks(2);
		var quantity = 100;

		//When
		service.saveOrder(toto, deliveryDate, quantity);

		//Then
		verify(repository).saveOrder(captor.capture());
		assertThat(captor.getValue().getRecipient()).isEqualTo(toto);
		assertThat(captor.getValue().getDeliveryDate()).isEqualTo(deliveryDate);
		assertThat(captor.getValue().getBananasQuantity()).isEqualTo(quantity);
	}

	@Test
	void should_update_order_when_delivery_date_one_week_after_current_date_and_valid_quantity() {
		//Given
		var toto = new Recipient(1, "Toto", "5 avenue Anatole France", "75007", "Paris", "France");
		var order = new Order(1, toto, LocalDate.now().plusYears(1), 50);

		//When
		service.updateOrder(order);

		//Then
		verify(repository).updateOrder(captor.capture());
		assertThat(captor.getValue()).isEqualTo(order);
	}

	@Test
	void should_remove_input_order() {
		//Given
		var toto = new Recipient(1, "Toto", "5 avenue Anatole France", "75007", "Paris", "France");
		var order = new Order(1, toto, LocalDate.now(), 50);

		//When
		service.removeOrder(order);

		//Then
		verify(repository).deleteOrder(captor.capture());
		assertThat(captor.getValue()).isEqualTo(order);
	}
}
