package com.fetch.app.receipt_processor_challenge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fetch.app.receipt_processor_challenge.entities.Receipt;
import com.fetch.app.receipt_processor_challenge.repositories.ReceiptRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main controller class for the Receipt Processor API. This class defines
 * endpoints for
 * creating receipts and viewing points
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

    public ReceiptProcessorChallengeController() {
    }

        private static final Logger logger = LoggerFactory.getLogger(Receipt.class);


    /**
     * Endpoint to add a new receipt to the system.
     *
     * @param receipt takes in the input of the Retailer, Purchase Date, Purchase Time,
     * list of items, id, and the total amount
     * the user
     * @return a ResponseEntity containing the newly created Receipt and a CREATED
     * status
     */
    @PostMapping("/receipts/process")
    public ResponseEntity<Receipt> processReceipts(@RequestBody Receipt receipt) {
        logger.info("TEST");
        receiptRepository.save(receipt);
        return ResponseEntity.ok(receipt);
    }
}
