package com.example.finalproject.Models;

public class Cart {
    String Id;
    int Quantity;
    Product Product;
    String OrderRequest;
    double Total;
    public Cart(){}
    public Cart(String Id,int Quantity,Product Product,String OrderRequest,double Total)
    {
        this.Id=Id;
        this.Quantity=Quantity;
        this.Product=Product;
        this.OrderRequest=OrderRequest;
        this.Total=Total;
    }
    public String getId() {
        return Id;
    }
    public void setId(String id) {
        Id = id;
    }
    public double getTotal() {
        return Total;
    }
    public void setTotal(double total) {
        Total = total;
    }
    public int getQuantity() {
        return Quantity;
    }
    public void setQuantity(int quantity) {
        Quantity = quantity;
    }
    public com.example.finalproject.Models.Product getProduct() {
        return Product;
    }
    public void setProduct(com.example.finalproject.Models.Product product) {
        Product = product;
    }
    public String getOrderRequest() {
        return OrderRequest;
    }
    public void setOrderRequest(String orderRequest) {
        OrderRequest = orderRequest;
    }
}
