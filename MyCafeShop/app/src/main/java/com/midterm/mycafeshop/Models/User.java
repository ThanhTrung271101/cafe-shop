package com.example.finalproject.Models;

public class User {
    String Id;
    String Name;
    String BirthDay;
    String Address;
    String Email;
    String PhoneNumber;
    int GiftPoint;
    int AccumulatedPoint;
    String Notification;
    String Role;
    public User() {
    }

    public User(String Id, String Name, String BirthDay, String Address, String Email, String PhoneNumber, int GiftPoint, int AccumulatedPoint,String Notification,String Role) {
        this.Id = Id;
        this.Name = Name;
        this.BirthDay = BirthDay;
        this.Address = Address;
        this.Email = Email;
        this.PhoneNumber = PhoneNumber;
        this.GiftPoint = GiftPoint;
        this.AccumulatedPoint = AccumulatedPoint;
        this.Notification=Notification;
        this.Role=Role;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getAccumulatedPoint() {
        return AccumulatedPoint;
    }

    public void setAccumulatedPoint(int accumulatedPoint) {
        AccumulatedPoint = accumulatedPoint;
    }

    public int getGiftPoint() {
        return GiftPoint;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getAddress() {
        return Address;
    }

    public void setBirthDay(String birthDay) {
        BirthDay = birthDay;
    }

    public String getBirthDay() {
        return BirthDay;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getEmail() {
        return Email;
    }

    public void setGiftPoint(int giftPoint) {
        GiftPoint = giftPoint;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getNotification() {
        return Notification;
    }

    public void setNotification(String notification) {
        Notification = notification;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }
}
