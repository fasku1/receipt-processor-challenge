package com.fetch.app.receipt_processor_challenge.entities;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;

@Entity
public class Receipt {
    @Id
    private String id;
    // The name of the retailer or store the receipt is from.
    private String retailer;
    // The date of the purchase printed on the receipt.
    private String purchaseDate;
    // The time of the purchase printed on the receipt. 24-hour time expected
    private String purchaseTime;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "receipt_id") // optional: tells JPA to use a foreign key
    private List<Item> items;
    // The total amount paid on the receipt.
    private String total;

    public Receipt(String retailer, String purchaseDate, String purchaseTime, List<Item> items) {
        this.id = UUID.randomUUID().toString(); // Auto-generate the UUID
        this.retailer = retailer;
        this.purchaseDate = purchaseDate;
        this.purchaseTime = purchaseTime;
        this.items = items;
    }

    public void Receipt(){};

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRetailer() {
        return retailer;
    }

    public void setRetailer(String retailer) {
        this.retailer = retailer;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getPurchaseTime() {
        return purchaseTime;
    }

    public void setPurchaseTime(String purchaseTime) {
        this.purchaseTime = purchaseTime;
    }

public List<Item> getItems() {
    return items;
}

public void setItems(List<Item> items) {
    this.items = items;
}

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
