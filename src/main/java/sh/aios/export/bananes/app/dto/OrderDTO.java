package sh.aios.export.bananes.app.dto;

import java.io.Serializable;
import java.time.LocalDate;

public class OrderDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private RecipientDTO recipient;

	private LocalDate deliveryDate;

	private Integer bananasQuantity;

	private Double price;

	public OrderDTO(Integer id, RecipientDTO recipient, LocalDate deliveryDate, Integer bananasQuantity, Double price) {
		this.id = id;
		this.recipient = recipient;
		this.deliveryDate = deliveryDate;
		this.bananasQuantity = bananasQuantity;
		this.price = price;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public RecipientDTO getRecipient() {
		return recipient;
	}

	public void setRecipient(RecipientDTO recipient) {
		this.recipient = recipient;
	}

	public LocalDate getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(LocalDate deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public Integer getBananasQuantity() {
		return bananasQuantity;
	}

	public void setBananasQuantity(Integer bananasQuantity) {
		this.bananasQuantity = bananasQuantity;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

}
