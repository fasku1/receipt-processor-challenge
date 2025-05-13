package com.fetch.app.receipt_processor_challenge.entities;
import com.fetch.app.receipt_processor_challenge.entities.Item;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.util.UUID;

@Entity
public class Receipt {
    @Id
    private String id;
    // The name of the retailer or store the receipt is from.
    private String retailer;
    // The date of the purchase printed on the receipt.
    private String purhcaseDate;
    // The time of the purchase printed on the receipt. 24-hour time expected
    private String purhcaseTime;
    private Item[] items;
    // The total amount paid on the receipt.
    private String total;

    public Receipt() {
        this.id = UUID.randomUUID().toString(); // Auto-generate the UUID
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

    public String getPurhcaseDate() {
        return purhcaseDate;
    }

    public void setPurhcaseDate(String purhcaseDate) {
        this.purhcaseDate = purhcaseDate;
    }

    public String getPurhcaseTime() {
        return purhcaseTime;
    }

    public void setPurhcaseTime(String purhcaseTime) {
        this.purhcaseTime = purhcaseTime;
    }

    public Item[] getItems() {
        return items;
    }

    public void setItems(Item[] items) {
        this.items = items;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
