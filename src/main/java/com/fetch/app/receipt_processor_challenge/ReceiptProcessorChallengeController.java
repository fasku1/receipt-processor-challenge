package com.fetch.app.receipt_processor_challenge;

import org.springframework.web.bind.annotation.*;

@RestController
public class ReceiptProcessorChallengeController {
    public ReceiptProcessorChallengeController(){
    }



    @GetMapping("/public/hello")
    public String hello() {
        return ("hey");
    }
}
