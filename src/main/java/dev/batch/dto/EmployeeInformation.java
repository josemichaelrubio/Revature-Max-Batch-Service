package dev.batch.dto;

import java.util.Objects;

public class EmployeeInformation {
	private String fullName;
	private String phone;
	private String address;
	private String picture;

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		EmployeeInformation that = (EmployeeInformation) o;
		return Objects.equals(fullName, that.fullName) && Objects.equals(phone, that.phone) && Objects.equals(address, that.address) && Objects.equals(picture, that.picture);
	}

	@Override
	public int hashCode() {
		return Objects.hash(fullName, phone, address, picture);
	}

	@Override
	public String toString() {
		return "EmployeeInformation{" +
				"fullName='" + fullName + '\'' +
				", phone='" + phone + '\'' +
				", address='" + address + '\'' +
				", picture='" + picture + '\'' +
				'}';
	}
}
