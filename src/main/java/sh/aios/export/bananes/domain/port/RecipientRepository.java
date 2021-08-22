package sh.aios.export.bananes.domain.port;

import java.util.List;

import sh.aios.export.bananes.domain.model.Recipient;

public interface RecipientRepository {

	List<Recipient> getRecipients();

	void saveRecipient(Recipient recipient);

	void updateRecipient(Recipient recipient);

	void deleteRecipient(Recipient recipient);

}
