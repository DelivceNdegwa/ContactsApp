package com.example.contactsapp.models;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class ContactDetails {
    String firstName, lastName, profileImage;
    int phoneNumber;
    int saveLocation;
    String image;
    boolean isFavorite;

    @Id
    long id;

    public ContactDetails(String firstName, String lastName, int phoneNumber, int saveLocation, boolean isFavorite) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.saveLocation = saveLocation;
        this.isFavorite = isFavorite;
    }

    public  ContactDetails(){

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

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}
