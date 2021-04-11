package entity;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "user")
public class User {

	@Id
	private String id;

	@Column(name = "firstName", nullable = false)
	private String firstName;

	@Column(name = "lastName", nullable = false)
	private String lastName;

	@Column(name = "emailAddress", nullable = false)
	private String emailAddress;

	@Column(name = "phoneNumber", nullable = false)
	private String phoneNumber;

	@Column(name = "username", nullable = false, unique = true)
	private String username;

	@Column(nullable = false)
	private String password;

	public User(String firstName, String lastName, String emailAddress, String phoneNumber, String username, String password) {
		this.id = UUID.randomUUID().toString();
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailAddress = emailAddress;
		this.phoneNumber = phoneNumber;
		this.username = username;
		this.password = password;
		//this.residences = residences;
	}

	public User() {}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		User user = (User) o;

		if (!id.equals(user.id)) return false;
		if (!firstName.equals(user.firstName)) return false;
		if (!lastName.equals(user.lastName)) return false;
		if (!emailAddress.equals(user.emailAddress)) return false;
		if (!phoneNumber.equals(user.phoneNumber)) return false;
		if (!username.equals(user.username)) return false;
		return password.equals(user.password);
	}

	@Override
	public int hashCode() {
		int result = id.hashCode();
		result = 31 * result + firstName.hashCode();
		result = 31 * result + lastName.hashCode();
		result = 31 * result + emailAddress.hashCode();
		result = 31 * result + phoneNumber.hashCode();
		result = 31 * result + username.hashCode();
		result = 31 * result + password.hashCode();
		return result;
	}
}
