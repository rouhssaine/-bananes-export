package sh.aios.export.bananes.app.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sh.aios.export.bananes.app.dto.RecipientDTO;
import sh.aios.export.bananes.domain.model.Recipient;
import sh.aios.export.bananes.domain.port.RecipientManagement;

@RestController
@RequestMapping(value = "/api/recipient")
public class RecipientController {

	private final RecipientManagement recipientManagement;

	public RecipientController(RecipientManagement recipientManagement) {
		this.recipientManagement = recipientManagement;
	}

	@GetMapping
	public List<RecipientDTO> retrieve() {
		return recipientManagement.consultRecipients().stream()
				.map(this::recipientToDTO)
				.collect(Collectors.toList());
	}

	@PostMapping
	public void create(@RequestParam final String name,
			@RequestParam final String address,
			@RequestParam final String postalCode,
			@RequestParam final String city,
			@RequestParam final String state) {
		recipientManagement.saveRecipient(name, address, postalCode, city, state);
	}

	@PutMapping
	public void update(@RequestBody final RecipientDTO recipient) {
		recipientManagement.updateRecipient(dtoToRecipient(recipient));
	}

	@DeleteMapping
	public void delete(@RequestBody final RecipientDTO recipient) {
		recipientManagement.removeRecipient(dtoToRecipient(recipient));
	}

	private RecipientDTO recipientToDTO(Recipient recipient) {
		return new RecipientDTO(recipient.getId(),
				recipient.getName(),
				recipient.getAddress(),
				recipient.getPostalCode(),
				recipient.getCity(),
				recipient.getState());
	}

	private Recipient dtoToRecipient(RecipientDTO dto) {
		return new Recipient(dto.getId(),
				dto.getName(),
				dto.getAddress(),
				dto.getPostalCode(),
				dto.getCity(),
				dto.getState());
	}

}
