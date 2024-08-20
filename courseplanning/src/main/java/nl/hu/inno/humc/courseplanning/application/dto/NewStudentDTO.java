package nl.hu.inno.humc.courseplanning.application.dto;

import java.util.UUID;

public class NewStudentDTO {
    private UUID id;
    private String name;
    private String phoneNumber;
    private String emailAddress;
    private String address;
    private String zipCode;
    private String city;

    public NewStudentDTO(UUID id, String name, String phoneNumber, String emailAddress, String address, String zipCode, String city) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.address = address;
        this.zipCode = zipCode;
        this.city = city;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getCity() {
        return city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
