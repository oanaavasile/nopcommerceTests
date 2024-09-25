package model;

import general.GenerateRandom;
import lombok.Data;

@Data
public class RandomRegistrationData {

    private String firstName;
    private String lastName;
    private String day;
    private String month;
    private String year;
    private String email;
    private String companyName;
    private String password;
    private String gender;

    public void generateRandomPersonalDetails() {
        firstName = GenerateRandom.generateRandomString(10);
        lastName = GenerateRandom.generateRandomString(10);
        day = String.valueOf(GenerateRandom.generateRandomNumber(31));
        month = GenerateRandom.randomMonth();
        year = GenerateRandom.randomYear(1950, 2000);
        email = GenerateRandom.generateEmail(10)+"@yopmail.com";

        int randomGender = GenerateRandom.generateRandomNumber(2);
        if (randomGender == 2) {
            gender = "Female";
        } else {
            gender = "Male";
        }

    }

    public void generateRandomCompany() {
        companyName = GenerateRandom.generateRandomString(10);
    }

    public void generateRandomPass() {
        password = GenerateRandom.generateRandomString(20);
    }

    public void generateRandomAll(){
        generateRandomPersonalDetails();
        generateRandomCompany();
        generateRandomPass();
    }

}
