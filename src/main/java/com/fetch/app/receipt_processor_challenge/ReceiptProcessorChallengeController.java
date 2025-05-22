package com.fetch.app.receipt_processor_challenge;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fetch.app.receipt_processor_challenge.entities.Item;
import com.fetch.app.receipt_processor_challenge.entities.Receipt;
import com.fetch.app.receipt_processor_challenge.repositories.ItemRepository;
import com.fetch.app.receipt_processor_challenge.repositories.ReceiptRepository;

import jakarta.validation.Valid;

/**
 * Main controller class for the Receipt Processor API. This class defines
 * endpoints for creating receipts and viewing points
 *
 * <p>
 * Bugs: None known
 *
 * @author Ethan Yang
 */
@RestController
public class ReceiptProcessorChallengeController {

    @Autowired
    private ReceiptRepository receiptRepository;
    @Autowired
    private ItemRepository itemRepository;

    public ReceiptProcessorChallengeController() {
    }

    /**
     * Endpoint to add a new receipt to the system.
     *
     * @param receipt takes in the input of the Retailer, Purchase Date,
     * Purchase Time, list of items, id, and the total amount the user
     * @return a ResponseEntity containing the newly created Receipt and a
     * CREATED status
     */
    @PostMapping("/receipts/process")
    public ResponseEntity<Map<String, String>> processReceipts(@Valid @RequestBody Receipt receipt) {
        Receipt savedReceipt = receiptRepository.save(receipt);
        Map<String, String> response = new HashMap<>();
        response.put("id", savedReceipt.getId().toString());
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint to add a new receipt to the system.
     *
     * @param id takes in the input of the receipt id outputted after a receipt
     * is made
     * @return an int that represents the total amount of points
     */
    @GetMapping("/receipts/{id}/points")
    public Map<String, Integer> getPoints(@PathVariable UUID id) {
        int points = 0; // default

        Receipt receipt = receiptRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Receipt not found with id: " + id));
        List<Item> items = itemRepository.findByReceiptId(id);

        String retailer = receipt.getRetailer();
        float total = receipt.getTotal();

        // One point for every alphanumeric character in the retailer name.
        for (int i = 0; i < retailer.length(); i++) {
            char character = retailer.charAt(i);
            boolean isLetter = Character.isLetter(character);
            if (isLetter) {
                points++;
            }
        }

        // 50 points if the total is a round dollar amount with no cents.
        if (total % 1.0 == 0) {
            points += 50;
        }

        // 25 points if the total is a multiple of 0.25.
        if (total % 0.25 == 0) {
            points += 25;
        }

        // 5 points for every two items on the receipt.
        points += Math.floor(items.size() / 2) * 5;

        // If the trimmed length of the item description is a multiple of 3, 
        // multiply the price by 0.2 and round up to the nearest integer. 
        // The result is the number of points earned.
        for (Item item : items) {
            String description = item.getShortDescription().trim();
            float price = item.getPrice();
            if ((description.length() != 0) && (description.length() % 3 == 0)) {
                int points_earned = (int) Math.ceil(price * 0.2);
                points += points_earned;
            }
        }

        // 6 points if the day in the purchase date is odd.
        LocalDate date = LocalDate.parse(receipt.getPurchaseDate());    // parse the string
        int day = date.getDayOfMonth();                                 // get the day (1)

        boolean isOdd = day % 2 == 1;                                   // true if day is odd
        if (isOdd) {
            points += 6;
        }

        // 10 points if the time of purchase is after 2:00pm and before 4:00pm.
        LocalTime purchaseTime = LocalTime.parse(receipt.getPurchaseTime());
        LocalTime start = LocalTime.of(14, 0);                          // 2:00 PM
        LocalTime end = LocalTime.of(16, 0);                            // 4:00 PM

        if (purchaseTime.isAfter(start) && purchaseTime.isBefore(end)) {
            points += 10;
        }

        Map<String, Integer> response = new HashMap<>();
        response.put("points", points);
        return response;
    }
}
