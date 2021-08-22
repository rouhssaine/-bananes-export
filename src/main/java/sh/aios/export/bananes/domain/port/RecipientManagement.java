package sh.aios.export.bananes.domain.port;

import java.util.List;

import sh.aios.export.bananes.domain.model.Recipient;

public interface RecipientManagement {

	List<Recipient> consultRecipients();

	void saveRecipient(String name, String address, String postalCode, String city, String state);

	void updateRecipient(Recipient recipient);

	void removeRecipient(Recipient recipient);

}
