package dto;

import java.util.ArrayList;
import java.util.List;

public class UsersTableDto {

    private String id;
    private String name;
    private String emailAddress;
    private String phoneNumber;
    private String residences;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getResidences() {
        return residences;
    }

    public void setResidences(String residences) {
        this.residences = residences;
    }
}
