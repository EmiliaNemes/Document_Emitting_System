package dto;

import entity.Request;
import entity.Residence;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserDto {

    private String id;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String phoneNumber;
    private String username;
    private String password;
    private List<ResidenceDto> residences = new ArrayList<>();
    private List<RequestDto> requests = new ArrayList<>();

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

    public List<ResidenceDto> getResidences() {
        return residences;
    }

    public void setResidences(List<ResidenceDto> residences) {
        this.residences = residences;
    }

    public List<RequestDto> getRequests() {
        return requests;
    }

    public void setRequests(List<RequestDto> requests) {
        this.requests = requests;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserDto userDto = (UserDto) o;

        if (!id.equals(userDto.id)) return false;
        if (!firstName.equals(userDto.firstName)) return false;
        if (!lastName.equals(userDto.lastName)) return false;
        if (!emailAddress.equals(userDto.emailAddress)) return false;
        if (!phoneNumber.equals(userDto.phoneNumber)) return false;
        if (!username.equals(userDto.username)) return false;
        if (!password.equals(userDto.password)) return false;
        if (!residences.equals(userDto.residences)) return false;
        return requests.equals(userDto.requests);
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
        result = 31 * result + residences.hashCode();
        result = 31 * result + requests.hashCode();
        return result;
    }
}
