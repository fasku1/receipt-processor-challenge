package com.fetch.app.receipt_processor_challenge.entities;

public class Item {
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
