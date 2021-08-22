package sh.aios.export.bananes.infra.adapter;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import sh.aios.export.bananes.domain.model.Recipient;
import sh.aios.export.bananes.domain.port.RecipientRepository;
import sh.aios.export.bananes.infra.entity.Customer;
import sh.aios.export.bananes.infra.repository.CustomerJpaRepository;

@Service
public class CustomerRepositoryAdapter implements RecipientRepository {

	private final CustomerJpaRepository customerJpaRepository;

	public CustomerRepositoryAdapter(CustomerJpaRepository customerJpaRepository) {
		this.customerJpaRepository = customerJpaRepository;
	}

	@Override
	public List<Recipient> getRecipients() {
		return StreamSupport.stream(customerJpaRepository.findAll().spliterator(), false)
				.map(this::customerToRecipient)
				.collect(Collectors.toList());
	}

	@Override
	public void saveRecipient(Recipient recipient) {
		customerJpaRepository.save(recipientToCustomer(recipient));

	}

	@Override
	public void updateRecipient(Recipient recipient) {
		customerJpaRepository.save(recipientToCustomer(recipient));
	}

	@Override
	public void deleteRecipient(Recipient recipient) {
		customerJpaRepository.delete(recipientToCustomer(recipient));
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

	private Recipient customerToRecipient(Customer customer) {
		return new Recipient(customer.getId(),
				customer.getName(),
				customer.getAddress(),
				customer.getPostalCode(),
				customer.getCity(),
				customer.getState());
	}

}
