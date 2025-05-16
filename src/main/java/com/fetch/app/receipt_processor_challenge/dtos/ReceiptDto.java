package com.fetch.app.receipt_processor_challenge.dtos;

import java.util.List;

import com.fetch.app.receipt_processor_challenge.entities.Item;

public class ReceiptDto {
    // The name of the retailer or store the receipt is from.
    private String retailer;
    // The date of the purchase printed on the receipt.
    private String purchaseDate;
    // The time of the purchase printed on the receipt. 24-hour time expected
    private String purchaseTime;
    private List<Item> items;
    // The total amount paid on the receipt.
    private String total;

    public ReceiptDto(String retailer, String purchaseDate, String purchaseTime) {
        this.retailer = retailer;
        this.purchaseDate = purchaseDate;
        this.purchaseTime = purchaseTime;
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

    public void setPurchaseTime(String purhcaseTime) {
        this.purchaseTime = purhcaseTime;
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
