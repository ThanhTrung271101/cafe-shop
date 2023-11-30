package com.example.finalproject.Models;

public class Order {
    String Id;
    String Address;
    String Name;
    String Phone;
    String Status;
    String TimeOrder;
    double Total;
    public Order(){}
    public Order(String Id,String Address,String Name,String Phone,String Status,String TimeOrder,double Total){
        this.Id=Id;
        this.Address=Address;
        this.Name=Name;
        this.Phone=Phone;
        this.Status=Status;
        this.TimeOrder=TimeOrder;
        this.Total=Total;
    }
    public String getId() {
        return Id;
    }
    public void setId(String id) {
        Id = id;
    }
    public String getAddress() {
        return Address;
    }
    public void setAddress(String address) {
        Address = address;
    }
    public String getName() {
        return Name;
    }
    public void setName(String name) {
        Name = name;
    }
    public double getTotal() {
        return Total;
    }
    public void setTotal(double total) {
        Total = total;
    }
    public String getPhone() {
        return Phone;
    }
    public void setPhone(String phone) {
        Phone = phone;
    }
    public String getStatus() {
        return Status;
    }
    public void setStatus(String status) {
        Status = status;
    }
    public String getTimeOrder() {
        return TimeOrder;
    }
    public void setTimeOrder(String timeOrder) {
        TimeOrder = timeOrder;
    }
}
