package com.fetch.app.receipt_processor_challenge.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // or AUTO
    private Long id;
    // The Short Product Description for the item.
    private String shortDescription;
    // The total price payed for this item.
    private String price;

    // Getters and setters
    public String getShortDescription(){
        return shortDescription;
    }

    public void setShortDescription(String shortDescription){
        this.shortDescription = shortDescription;
    }

    public String getPrice(){
        return price;
    }

    public void setPrice(String price){
        this.price = price;
    }
}
