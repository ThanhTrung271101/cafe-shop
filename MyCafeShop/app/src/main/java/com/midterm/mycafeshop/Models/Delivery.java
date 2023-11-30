package com.example.finalproject.Models;

public class Delivery {
    String Id;
    String Name;
    String Address;
    String Email;
    String Phone;
    String Note;
    public Delivery(){}
    public Delivery(String Id, String Name,String Address,String Email,String Phone,String Note){
        this.Id=Id;
        this.Name=Name;
        this.Address=Address;
        this.Email=Email;
        this.Phone=Phone;
        this.Note=Note;
    }

    public String getPhone() {
        return Phone;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getName() {
        return Name;
    }

    public String getAddress() {
        return Address;
    }

    public String getId() {
        return Id;
    }

    public String getEmail() {
        return Email;
    }

    public String getNote() {
        return Note;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setId(String id) {
        Id = id;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setNote(String note) {
        Note = note;
    }
}
