public package com.example.finalproject.Models;

import java.util.Date;

public class Detail {
    String Id;
    Product product;
    String date;
    public Detail(){};
    public Detail(String Id,Product product,String date)
    {
        this.Id=Id;
        this.product=product;
        this.date=date;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
 Detail {
    
}
