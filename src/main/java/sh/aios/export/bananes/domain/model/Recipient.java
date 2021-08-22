package sh.aios.export.bananes.domain.model;

import java.util.Objects;

public class Recipient {

	private Integer id;

	private String name;

	private String address;

	private String postalCode;

	private String city;

	private String state;

	public Recipient(Integer id, String name, String address, String postalCode, String city, String state) {
		this.id = id;
		this.name = name;
		this.address = address;
		this.postalCode = postalCode;
		this.city = city;
		this.state = state;
	}

	public Recipient(String name, String address, String postalCode, String city, String state) {
		this.name = name;
		this.address = address;
		this.postalCode = postalCode;
		this.city = city;
		this.state = state;
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getAddress() {
		return address;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public String getCity() {
		return city;
	}

	public String getState() {
		return state;
	}

	@Override
	public int hashCode() {
		return Objects.hash(address, city, name, postalCode, state);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Recipient))
			return false;
		Recipient other = (Recipient) obj;
		return Objects.equals(address, other.address) && Objects.equals(city, other.city) && Objects.equals(name, other.name)
				&& Objects.equals(postalCode, other.postalCode) && Objects.equals(state, other.state);
	}

}
