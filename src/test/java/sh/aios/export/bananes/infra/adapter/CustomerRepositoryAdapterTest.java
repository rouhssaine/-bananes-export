package sh.aios.export.bananes.infra.adapter;

import static org.assertj.core.api.Assertions.assertThat;
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
import sh.aios.export.bananes.infra.entity.Customer;
import sh.aios.export.bananes.infra.repository.CustomerJpaRepository;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CustomerRepositoryAdapterTest {

	@InjectMocks
	private CustomerRepositoryAdapter adapter;

	@Mock
	private CustomerJpaRepository repository;

	@Captor
	private ArgumentCaptor<Customer> captor;

	@Test
	void should_return_all_customers() {
		//Given
		var customer = createCustomer();
		when(repository.findAll()).thenReturn(List.of(customer));

		//When
		var result = adapter.getRecipients();

		//Then
		assertThat(result).hasSize(1);
	}

	@Test
	void should_save_customer() {
		//Given
		var toto = new Recipient(1, "Toto", "5 avenue Anatole France", "75007", "Paris", "France");

		//When
		adapter.saveRecipient(toto);

		//Then
		verify(repository).save(captor.capture());
		assertThat(captor.getValue().getId()).isEqualTo(1);
		assertThat(captor.getValue().getName()).isEqualTo("Toto");
		assertThat(captor.getValue().getAddress()).isEqualTo("5 avenue Anatole France");
		assertThat(captor.getValue().getPostalCode()).isEqualTo("75007");
		assertThat(captor.getValue().getCity()).isEqualTo("Paris");
		assertThat(captor.getValue().getState()).isEqualTo("France");
	}

	@Test
	void should_update_customer() {
		//Given
		var toto = new Recipient(1, "Toto", "5 avenue Anatole France", "75007", "Paris", "France");

		//When
		adapter.updateRecipient(toto);

		//Then
		verify(repository).save(captor.capture());
		assertThat(captor.getValue().getId()).isEqualTo(1);
		assertThat(captor.getValue().getName()).isEqualTo("Toto");
		assertThat(captor.getValue().getAddress()).isEqualTo("5 avenue Anatole France");
		assertThat(captor.getValue().getPostalCode()).isEqualTo("75007");
		assertThat(captor.getValue().getCity()).isEqualTo("Paris");
		assertThat(captor.getValue().getState()).isEqualTo("France");
	}

	@Test
	void should_delete_customer() {
		//Given
		var toto = new Recipient(1, "Toto", "5 avenue Anatole France", "75007", "Paris", "France");

		//When
		adapter.deleteRecipient(toto);

		//Then
		verify(repository).delete(captor.capture());
		assertThat(captor.getValue().getId()).isEqualTo(1);
		assertThat(captor.getValue().getName()).isEqualTo("Toto");
		assertThat(captor.getValue().getAddress()).isEqualTo("5 avenue Anatole France");
		assertThat(captor.getValue().getPostalCode()).isEqualTo("75007");
		assertThat(captor.getValue().getCity()).isEqualTo("Paris");
		assertThat(captor.getValue().getState()).isEqualTo("France");
	}

	private Customer createCustomer() {
		Customer customer = new Customer();
		customer.setId(1);
		customer.setName("Toto");
		customer.setAddress("5 avenue Anatole France");
		customer.setPostalCode("75007");
		customer.setCity("Paris");
		customer.setState("France");
		return customer;
	}
}
