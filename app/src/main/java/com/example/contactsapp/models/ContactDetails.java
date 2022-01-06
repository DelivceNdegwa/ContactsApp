package com.example.contactsapp.models;

public class ContactDetails {
    String firstName, lastName, profileImage;
    int phoneNumber;
    int saveLocation;

    public ContactDetails(String firstName, String lastName, int phoneNumber, int saveLocation) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.saveLocation = saveLocation;
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

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getSaveLocation() {
        return saveLocation;
    }

    public void setSaveLocation(int saveLocation) {
        this.saveLocation = saveLocation;
    }
}
