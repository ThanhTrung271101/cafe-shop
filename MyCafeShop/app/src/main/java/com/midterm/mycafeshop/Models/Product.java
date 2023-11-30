package com.example.finalproject.Models;

public class Product {
    String Id;
    String Name;
    String Price;
    String Price_Point;
    String Quantity;
    String Image;
    Category Category;
    public Product(){}
    public Product(String Id,String Name,String Price,String Price_Point,String Quantity, String Image,Category category){
        this.Id=Id;
        this.Name=Name;
        this.Price=Price;
        this.Price_Point=Price_Point;
        this.Quantity=Quantity;
        this.Image=Image;
        this.Category=category;
    }
    public Product(String Name,String Image,String Price){
        this.Name=Name;
        this.Image=Image;
        this.Price=Price;
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
    public String getPrice() {
        return Price;
    }
    public void setPrice(String price) {
        Price = price;
    }
    public String getPrice_Point() {
        return Price_Point;
    }
    public void setPrice_Point(String price_Point) {
        Price_Point = price_Point;
    }
    public String getImage() {
        return Image;
    }
    public void setImage(String image) {
        Image = image;
    }
    public String getQuantity() {
        return Quantity;
    }
    public void setQuantity(String quantity) {
        Quantity = quantity;
    }
    public com.example.finalproject.Models.Category getCategory() {
        return Category;
    }
    public void setCategory(com.example.finalproject.Models.Category category) {
        Category = category;
    }
}