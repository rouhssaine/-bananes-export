package sh.aios.export.bananes.domain.model;

import java.time.LocalDate;

public class Order {

	private Integer id;

	private Recipient recipient;

	private LocalDate deliveryDate;

	private int bananasQuantity;

	public Order(Recipient recipient, LocalDate deliveryDate, int bananasQuantity) {
		this.recipient = recipient;
		this.deliveryDate = deliveryDate;
		this.bananasQuantity = bananasQuantity;
	}

	public Order(Integer id, Recipient recipient, LocalDate deliveryDate, int bananasQuantity) {
		this.id = id;
		this.recipient = recipient;
		this.deliveryDate = deliveryDate;
		this.bananasQuantity = bananasQuantity;
	}

	public Integer getId() {
		return id;
	}

	public Recipient getRecipient() {
		return recipient;
	}

	public LocalDate getDeliveryDate() {
		return deliveryDate;
	}

	public int getBananasQuantity() {
		return bananasQuantity;
	}

	public double getPrice() {
		return bananasQuantity * 2.5;
	}

}
