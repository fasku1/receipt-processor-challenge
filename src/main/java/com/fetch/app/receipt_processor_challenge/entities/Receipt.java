package com.fetch.app.receipt_processor_challenge.entities;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;

@Entity
public class Receipt {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false)
    private UUID id;
    // The name of the retailer or store the receipt is from.
    @NotNull(message = "retailer must not be null")
    private String retailer;
    // The date of the purchase printed on the receipt.
    @NotNull
    private String purchaseDate;
    // The time of the purchase printed on the receipt. 24-hour time expected
    @NotNull
    private String purchaseTime;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "receipt_id") // optional: tells JPA to use a foreign key
    private List<Item> items;
    // The total amount paid on the receipt.
    private float total;

    public Receipt() {
    }

    public void Receipt(){};

    // Getters and setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
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
    this.total = 0.0f;
    if (items != null) {
        for (Item item : items) {
            this.total += item.getPrice();
        }
    }
}

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }
}
