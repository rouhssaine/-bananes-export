package sh.aios.export.bananes.app.controller;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import sh.aios.export.bananes.domain.model.Order;
import sh.aios.export.bananes.domain.model.Recipient;
import sh.aios.export.bananes.domain.port.OrderManagement;

@WebMvcTest(OrderController.class)
public class OrderControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private OrderManagement orderManagement;

	@BeforeEach
	void setup() {
		RestAssuredMockMvc.mockMvc(mockMvc);
	}

	@Test
	@DisplayName("Consulter la liste des commandes d'un destinataire")
	void should_return_orders_of_recipient() {
		var toto = new Recipient(1, "Toto", "5 avenue Anatole France", "75007", "Paris", "France");
		var totoOrder1 = new Order(1, toto, LocalDate.now(), 50);
		var totoOrder2 = new Order(1, toto, LocalDate.now(), 500);
		when(orderManagement.consultOrders(any(Recipient.class))).thenReturn(List.of(totoOrder1, totoOrder2));

		given()
				.auth().none()
				.param("{\"id\": 100, \"name\": \"TOTO\", \"address\": \"5 avenue Anatole France\", \"postalCode\": \"75007\", \"city\": \"Paris\", \"state\": \"France\" }")
				.when()
				.get("/api/order")
				.then()
				.statusCode(200)
				.body("$.size()", equalTo(2));
	}

	@Test
	@DisplayName("Enregistre une commande")
	void should_save_order() {
		given()
				.auth().none()
				.contentType("application/json")
				.when()
				.post("/api/order?id=100&name=TOTO&address=5 avenue Anatole France&postalCode=75007&city=Paris&state=France&deliveryDate=2021-12-15&quantity=200")
				.then()
				.statusCode(200);

		verify(orderManagement).saveOrder(any(Recipient.class), any(LocalDate.class), anyInt());
	}

	@Test
	@DisplayName("Met à jour une commande")
	void should_update_order() {
		given()
				.auth().none()
				.contentType("application/json")
				.body("{" +
						"  \"id\": 1," +
						"  \"recipient\": {" +
						"    \"id\": 10," +
						"    \"name\": \"Toto\"," +
						"    \"address\": \"5 avenue Anatole France\"," +
						"    \"postalCode\": \"75007\"," +
						"    \"city\": \"Paris\"," +
						"    \"state\": \"France\"" +
						"  }," +
						"  \"deliveryDate\": \"2025-08-22\"," +
						"  \"bananasQuantity\": 100," +
						"  \"price\": 250" +
						"}")
				.when()
				.put("/api/order")
				.then()
				.statusCode(200);

		verify(orderManagement).updateOrder(any(Order.class));
	}

	@Test
	@DisplayName("Supprime une commande")
	void should_delete_order() {
		given()
				.auth().none()
				.contentType("application/json")
				.body("{" +
						"  \"id\": 1," +
						"  \"recipient\": {" +
						"    \"id\": 10," +
						"    \"name\": \"Toto\"," +
						"    \"address\": \"5 avenue Anatole France\"," +
						"    \"postalCode\": \"75007\"," +
						"    \"city\": \"Paris\"," +
						"    \"state\": \"France\"" +
						"  }," +
						"  \"deliveryDate\": \"2025-08-22\"," +
						"  \"bananasQuantity\": 100," +
						"  \"price\": 250" +
						"}")
				.when()
				.delete("/api/order")
				.then()
				.statusCode(200);

		verify(orderManagement).removeOrder(any(Order.class));
	}
}
