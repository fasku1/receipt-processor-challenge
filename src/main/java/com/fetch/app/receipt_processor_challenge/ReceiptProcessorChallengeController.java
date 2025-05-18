package com.fetch.app.receipt_processor_challenge;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(Receipt.class);

    /**
     * Endpoint to add a new receipt to the system.
     *
     * @param receipt takes in the input of the Retailer, Purchase Date,
     * Purchase Time, list of items, id, and the total amount the user
     * @return a ResponseEntity containing the newly created Receipt and a
     * CREATED status
     */
    @PostMapping("/receipts/process")
    public ResponseEntity<Receipt> processReceipts(@RequestBody Receipt receipt) {
        receiptRepository.save(receipt);
        return ResponseEntity.ok(receipt);
    }

    @GetMapping("/receipts/{id}/points")
    public int getPoints(@PathVariable String id) {
        int points = 0;

        Receipt receipt = receiptRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Receipt not found with id: " + id));
        List<Item> items = itemRepository.findByReceiptId(id);

        String retailer = receipt.getRetailer();
        float total = receipt.getTotal();
        for (int i = 0; i < retailer.length(); i++) {
            char character = retailer.charAt(i);
            boolean isLetter = Character.isLetter(character);
            if (isLetter) {
                logger.info("Character: " + character + "; Points 1");
                points++;
            }
        }

        if (total % 1.0 == 0) {
            logger.info("total is a round dollar amount; Points 50");
            points += 50;
        } if (total % 0.25 == 0) {
            logger.info("total is a multiple of 0.25; Points 25");
            points += 25;
        }
        logger.info("Whatever items * 5 points, " + Math.floor(items.size() / 2));
        points += Math.floor(items.size() / 2) * 5;

        for (Item item : items) {
            String description = item.getShortDescription().trim();
            float price = item.getPrice();
            if (description.length() % 3 == 0) {
                logger.info("description length: " + description.length());
                logger.info("price*0.2: " + (price * 0.2));
                logger.info("ceiling: " + (int) Math.ceil(price * 0.2));
                int points_earned = (int) Math.ceil(price * 0.2);
                logger.info("Length of description is divsible by 3, " + points_earned);
                points += points_earned;
            }
        }

        LocalDate date = LocalDate.parse(receipt.getPurchaseDate()); // parse the string
        int day = date.getDayOfMonth();            // get the day (1)
        boolean isOdd = day % 2 == 1;              // true if day is odd
        if (isOdd) {
            logger.info("Date is odd, 6 points");
            points += 6;
        }

        LocalTime now = LocalTime.now(); // current time
        LocalTime start = LocalTime.of(14, 0); // 2:00 PM
        LocalTime end = LocalTime.of(16, 0);   // 4:00 PM

        if (now.isAfter(start) && now.isBefore(end)) {
            logger.info("Time is between 2pm and 4pm, 10 points");
            points += 10;
        }

        // You can calculate points here using receipt data
        return points;
    }
}
