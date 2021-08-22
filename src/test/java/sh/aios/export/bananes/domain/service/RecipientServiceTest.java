package sh.aios.export.bananes.domain.service;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

import sh.aios.export.bananes.domain.model.Recipient;
import sh.aios.export.bananes.domain.port.RecipientRepository;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class RecipientServiceTest {

	@InjectMocks
	private RecipientService service;

	@Mock
	private RecipientRepository repository;

	@Captor
	private ArgumentCaptor<Recipient> captor;

	@Test
	void should_return_all_recipients_when_consult_recipients() {
		//Given
		var toto = new Recipient(1, "Toto", "5 avenue Anatole France", "75007", "Paris", "France");
		var titi = new Recipient(1, "Titi", "5 avenue des Champs-Elysées", "75008", "Paris", "France");
		var recipients = List.of(toto, titi);
		when(repository.getRecipients()).thenReturn(recipients);

		//When
		List<Recipient> result = service.consultRecipients();

		//Then
		assertThat(result).hasSize(2).containsExactlyInAnyOrder(toto, titi);
	}

	@Test
	void should_not_save_order_when_exist_identical_recipient() {
		//Given
		var toto = new Recipient("Toto", "5 avenue Anatole France", "75007", "Paris", "France");
		when(repository.getRecipients()).thenReturn(List.of(toto));

		//When & Then
		assertThatThrownBy(() -> service.saveRecipient("Toto", "5 avenue Anatole France", "75007", "Paris", "France"))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Deux destinataire parfaitement identique")
				.hasNoCause();
	}

	@Test
	void should_save_order_when_not_exist_identical_recipient() {
		//Given
		when(repository.getRecipients()).thenReturn(emptyList());

		//When
		service.saveRecipient("Toto", "5 avenue Anatole France", "75007", "Paris", "France");

		//Then
		verify(repository).saveRecipient(captor.capture());
		assertThat(captor.getValue().getName()).isEqualTo("Toto");
		assertThat(captor.getValue().getAddress()).isEqualTo("5 avenue Anatole France");
	}

	@Test
	void should_not_update_order_when_exist_identical_recipient() {
		//Given
		var toto = new Recipient("Toto", "5 avenue Anatole France", "75007", "Paris", "France");
		when(repository.getRecipients()).thenReturn(List.of(toto));

		//When & Then
		assertThatThrownBy(() -> service.updateRecipient(toto))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Deux destinataire parfaitement identique")
				.hasNoCause();
	}

	@Test
	void should_update_order_when_not_exist_identical_recipient() {
		//Given
		var toto = new Recipient(1, "Toto", "5 avenue Anatole France", "75007", "Paris", "France");

		//When
		service.updateRecipient(toto);

		//Then
		verify(repository).updateRecipient(captor.capture());
		assertThat(captor.getValue()).isEqualTo(toto);
	}

	@Test
	void should_remove_input_order() {
		//Given
		var toto = new Recipient(1, "Toto", "5 avenue Anatole France", "75007", "Paris", "France");

		//When
		service.removeRecipient(toto);

		//Then
		verify(repository).deleteRecipient(captor.capture());
		assertThat(captor.getValue()).isEqualTo(toto);
	}
}
