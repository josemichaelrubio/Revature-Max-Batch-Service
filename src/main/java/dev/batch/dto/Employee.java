package dev.batch.dto;

import java.util.Objects;
import java.util.Set;

public class Employee {
	private Long id;
	private String email;
	// string or mirror the enum
	private String role;
	private String name;
	private String phoneNumber;
	private String address;
	// probably don't need it, but I'll mirror the DTO
	private String pictureUrl;


	public Employee() {
	}

	public Employee(Long id, String email, String role) {
		this.id = id;
		this.email = email;
		this.role = role;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Employee employee = (Employee) o;
		return Objects.equals(id, employee.id) && Objects.equals(email, employee.email) && Objects.equals(role, employee.role) && Objects.equals(name, employee.name) && Objects.equals(phoneNumber, employee.phoneNumber) && Objects.equals(address, employee.address) && Objects.equals(pictureUrl, employee.pictureUrl);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, email, role, name, phoneNumber, address, pictureUrl);
	}

	@Override
	public String toString() {
		return "Employee{" +
				"id=" + id +
				", email='" + email + '\'' +
				", role='" + role + '\'' +
				", name='" + name + '\'' +
				", phoneNumber='" + phoneNumber + '\'' +
				", address='" + address + '\'' +
				", pictureUrl='" + pictureUrl + '\'' +
				'}';
	}
}
