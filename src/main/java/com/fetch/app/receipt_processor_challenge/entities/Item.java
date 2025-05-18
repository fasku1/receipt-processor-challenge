package com.fetch.app.receipt_processor_challenge.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // or AUTO
    private Long id;
    // The Short Product Description for the item.
    private String shortDescription;
    // The total price payed for this item.
    private float price;
    @ManyToOne
    @JoinColumn(name = "receipt_id")
    private Receipt receipt;
    // Getters and setters

    public String getShortDescription(){
        return shortDescription;
    }

    public void setShortDescription(String shortDescription){
        this.shortDescription = shortDescription;
    }

    public float getPrice(){
        return price;
    }

    public void setPrice(float price){
        this.price = price;
    }
}
