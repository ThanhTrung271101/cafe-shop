package com.example.finalproject.Models;

import java.util.Date;

public class Detail1 {
    String Id;
    Product product;
    double Total;
    int Quantity;
    String OrderRequest;
    String IdCart;
    public Detail1(){};
    public Detail1(String Id,Product product,double total,int quanlity,String OrderRequest,String Idcart)
    {
        this.Id=Id;
        this.product=product;
        this.Total=total;
        this.Quantity=quanlity;
        this.OrderRequest=OrderRequest;
        this.IdCart=Idcart;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public int getQuanlity() {
        return Quantity;
    }

    public void setTotal(double total) {
        Total = total;
    }

    public double getTotal() {
        return Total;
    }

    public String getIdCart() {
        return IdCart;
    }

    public void setIdCart(String idCart) {
        IdCart = idCart;
    }

    public void setQuanlity(int quanlity) {
        Quantity = quanlity;
    }
    public String getOrderRequest() {
        return OrderRequest;
    }
    public void setOrderRequest(String orderRequest) {
        OrderRequest = orderRequest;
    }
}
