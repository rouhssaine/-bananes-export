package sh.aios.export.bananes.app.controller;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import sh.aios.export.bananes.domain.model.Recipient;
import sh.aios.export.bananes.domain.port.RecipientManagement;

@WebMvcTest(RecipientController.class)
public class RecipientControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private RecipientManagement recipientManagement;

	@BeforeEach
	void setup() {
		RestAssuredMockMvc.mockMvc(mockMvc);
	}

	@Test
	@DisplayName("Consulter la liste des destinataires")
	void should_return_recipients() {
		var toto = new Recipient(1, "Toto", "5 avenue Anatole France", "75007", "Paris", "France");
		var titi = new Recipient(1, "Titi", "5 avenue des Champs-Elysées", "75008", "Paris", "France");
		when(recipientManagement.consultRecipients()).thenReturn(List.of(toto, titi));

		given()
				.auth().none()
				.when()
				.get("/api/recipient")
				.then()
				.statusCode(200)
				.body("$.size()", equalTo(2));
	}

	@Test
	@DisplayName("Enregistre un destinataire")
	void should_save_recipient() {
		given()
				.auth().none()
				.param("name", "Toto")
				.param("address", "5 avenue Anatole France")
				.param("postalCode", "75007")
				.param("city", "Paris")
				.param("state", "France")
				.when()
				.post("/api/recipient")
				.then()
				.statusCode(200);

		verify(recipientManagement).saveRecipient("Toto", "5 avenue Anatole France", "75007", "Paris", "France");
	}

	@Test
	@DisplayName("Met à jour un destinataire")
	void should_update_recipient() {
		given()
				.auth().none()
				.contentType("application/json")
				.body("{" +
						" \"id\": 10," +
						" \"name\": \"Toto\"," +
						" \"address\": \"5 avenue Anatole France\"," +
						" \"postalCode\": \"75007\"," +
						" \"city\": \"Paris\"," +
						" \"state\": \"France\"" +
						"}")
				.when()
				.put("/api/recipient")
				.then()
				.statusCode(200);

		verify(recipientManagement).updateRecipient(any(Recipient.class));
	}

	@Test
	@DisplayName("Supprime un destinataire")
	void should_delete_recipient() {
		given()
				.auth().none()
				.contentType("application/json")
				.body("{" +
						" \"id\": 10," +
						" \"name\": \"Toto\"," +
						" \"address\": \"5 avenue Anatole France\"," +
						" \"postalCode\": \"75007\"," +
						" \"city\": \"Paris\"," +
						" \"state\": \"France\"" +
						"}")
				.when()
				.delete("/api/recipient")
				.then()
				.statusCode(200);

		verify(recipientManagement).removeRecipient(any(Recipient.class));
	}
}