package com.fetch.app.receipt_processor_challenge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fetch.app.receipt_processor_challenge.repositories.ReceiptRepository;
import com.fetch.app.receipt_processor_challenge.entities.Receipt;

@RestController
public class ReceiptProcessorChallengeController {

    @Autowired
    private ReceiptRepository receiptRepository;

    public ReceiptProcessorChallengeController() {
    }

    @PostMapping("/receipts/process")
    public ResponseEntity<Receipt> processReceipts(@RequestBody Receipt receipt) {
        return ResponseEntity.ok(receipt);
    }
}
