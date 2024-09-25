package model;

import java.util.Objects;

public class BillingDetails {
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String country;
    private final String state;
    private final String city;
    private final String address;
    private final String postalCode;
    private final String phoneNumber;

    // Optional fields
    private String company;
    private String address2;
    private String faxNumber;

    public BillingDetails(String firstName, String lastName, String email,
                          String country, String state, String city,
                          String address, String postalCode, String phoneNumber) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.country = country;
        this.state = state;
        this.city = city;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public void setFaxNumber(String faxNumber) {
        this.faxNumber = faxNumber;
    }

    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getCountry() { return country; }
    public String getState() { return state; }
    public String getCity() { return city; }
    public String getAddress() { return address; }
    public String getPostalCode() { return postalCode; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getCompany() { return company; }
    public String getAddress2() { return address2; }
    public String getFaxNumber() { return faxNumber; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BillingDetails that = (BillingDetails) o;
        return Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(email, that.email) &&
                Objects.equals(country, that.country)
                && Objects.equals(state, that.state)
                && Objects.equals(city, that.city)
                && Objects.equals(address, that.address)
                && Objects.equals(postalCode, that.postalCode)
                && Objects.equals(phoneNumber, that.phoneNumber)
                && Objects.equals(company, that.company)
                && Objects.equals(address2, that.address2)
                && Objects.equals(faxNumber, that.faxNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, email, country, state, city, address, postalCode, phoneNumber, company, address2, faxNumber);
    }
}
