package sh.aios.export.bananes.domain.service;

import java.util.List;

import com.google.common.base.Preconditions;

import sh.aios.export.bananes.domain.model.Recipient;
import sh.aios.export.bananes.domain.port.RecipientManagement;
import sh.aios.export.bananes.domain.port.RecipientRepository;

public class RecipientService implements RecipientManagement {

	private static final String IDENTICAL_RECIPIENT_ERROR_MESSAGE = "Deux destinataire parfaitement identique";

	private final RecipientRepository recipientRepository;

	public RecipientService(RecipientRepository recipientRepository) {
		this.recipientRepository = recipientRepository;
	}

	@Override
	public List<Recipient> consultRecipients() {
		return recipientRepository.getRecipients();
	}

	@Override
	public void saveRecipient(String name, String address, String postalCode, String city, String state) {
		Recipient recipient = new Recipient(name, address, postalCode, city, state);
		Preconditions.checkArgument(hasNotIdenticalRecipient(recipient), IDENTICAL_RECIPIENT_ERROR_MESSAGE);
		recipientRepository.saveRecipient(recipient);
	}

	@Override
	public void updateRecipient(Recipient recipient) {
		Preconditions.checkArgument(hasNotIdenticalRecipient(recipient), IDENTICAL_RECIPIENT_ERROR_MESSAGE);
		recipientRepository.updateRecipient(recipient);
	}

	@Override
	public void removeRecipient(Recipient recipient) {
		recipientRepository.deleteRecipient(recipient);
	}

	private boolean hasNotIdenticalRecipient(Recipient recipient) {
		return recipientRepository.getRecipients().stream()
				.noneMatch(recipient::equals);
	}

}
