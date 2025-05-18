package com.fetch.app.receipt_processor_challenge.controller_tests;

import com.fetch.app.receipt_processor_challenge.entities.Receipt;
import com.fetch.app.receipt_processor_challenge.entities.Item;
import com.fetch.app.receipt_processor_challenge.repositories.ReceiptRepository;
import com.fetch.app.receipt_processor_challenge.repositories.ItemRepository;
import com.fetch.app.receipt_processor_challenge.ReceiptProcessorChallengeController;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReceiptProcessorChallengeController.class)
class ReceiptControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReceiptRepository receiptRepository;

    @MockBean
    private ItemRepository itemRepository;;

    @Test
    void testProcessReceipts_ReturnsSavedReceipt() throws Exception {
        String receiptJson = """
            {
              "retailer": "Target",
              "purchaseDate": "2022-01-01",
              "purchaseTime": "13:01",
              "items": [
                {"shortDescription": "Mountain Dew", "price": "1.50"},
                {"shortDescription": "Doritos", "price": "2.00"}
              ],
              "total": "3.50"
            }
        """;

        // Simulate the repository saving and returning the same Receipt object
        Receipt dummyReceipt = new Receipt();
        dummyReceipt.setRetailer("Target");
        dummyReceipt.setPurchaseDate("2022-01-01");
        dummyReceipt.setPurchaseTime("13:01");
        Item item1 = new Item();
        item1.setShortDescription("Mountain Dew");
        item1.setPrice(1.50f);

        Item item2 = new Item();
        item2.setShortDescription("Doritos");
        item2.setPrice(2.00f);

        dummyReceipt.setItems(List.of(item1, item2));
        dummyReceipt.setTotal(3.50f);

        when(receiptRepository.save(any(Receipt.class))).thenReturn(dummyReceipt);

        mockMvc.perform(post("/receipts/process")
                .contentType(MediaType.APPLICATION_JSON)
                .content(receiptJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.retailer").value("Target"))
                .andExpect(jsonPath("$.items.length()").value(2))
                .andExpect(jsonPath("$.total").value(3.50f));
    }
}
