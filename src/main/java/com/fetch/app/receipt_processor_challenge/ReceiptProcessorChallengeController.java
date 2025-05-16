package com.fetch.app.receipt_processor_challenge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fetch.app.receipt_processor_challenge.repositories.ReceiptRepository;
import com.fetch.app.receipt_processor_challenge.dtos.ReceiptDto;

@RestController
public class ReceiptProcessorChallengeController {

    @Autowired
    private ReceiptRepository receiptRepository;

    public ReceiptProcessorChallengeController() {
    }

    @PostMapping("/receipts/process")
    public ResponseEntity<ReceiptDto> processReceipts(@RequestBody ReceiptDto receiptDto) {
        return ResponseEntity.ok(receiptDto);
    }
}
