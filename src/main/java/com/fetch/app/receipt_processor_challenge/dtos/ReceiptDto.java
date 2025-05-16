package com.fetch.app.receipt_processor_challenge.dtos;

import com.fetch.app.receipt_processor_challenge.entities.Item;
import java.util.List;

public class ReceiptDto {
    // The name of the retailer or store the receipt is from.
    private String retailer;
    // The date of the purchase printed on the receipt.
    private String purhcaseDate;
    // The time of the purchase printed on the receipt. 24-hour time expected
    private String purhcaseTime;
    private List<Item> items;
    // The total amount paid on the receipt.
    private String total;

    public ReceiptDto(String retailer, String purhcaseDate, String purhcaseTime) {
        this.retailer = retailer;
        this.purhcaseDate = purhcaseDate;
        this.purhcaseTime = purhcaseTime;
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
