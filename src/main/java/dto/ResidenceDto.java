package dto;

import entity.User;

public class ResidenceDto {

    private String id;
    private String city;
    private String street;
    private int number;
    private User user;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString(){
        return city + ", str. " + street + ", nr. " + number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ResidenceDto that = (ResidenceDto) o;

        if (number != that.number) return false;
        if (!id.equals(that.id)) return false;
        if (!city.equals(that.city)) return false;
        if (!street.equals(that.street)) return false;
        return user.equals(that.user);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + city.hashCode();
        result = 31 * result + street.hashCode();
        result = 31 * result + number;
        result = 31 * result + user.hashCode();
        return result;
    }
}
