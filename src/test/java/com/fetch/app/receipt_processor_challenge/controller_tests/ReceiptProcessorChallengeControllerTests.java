package com.fetch.app.receipt_processor_challenge.controller_tests;

import com.fetch.app.receipt_processor_challenge.entities.Receipt;
import com.fetch.app.receipt_processor_challenge.entities.Item;
import com.fetch.app.receipt_processor_challenge.repositories.ReceiptRepository;
import com.fetch.app.receipt_processor_challenge.repositories.ItemRepository;
import com.fetch.app.receipt_processor_challenge.ReceiptProcessorChallengeController;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
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
    private ItemRepository itemRepository;

    private Receipt dummyReceipt;

    private List<Item> items;

    @BeforeEach
    void setUp() {
        // Clear database (optional, depends on test isolation needs)
        itemRepository.deleteAll();
        receiptRepository.deleteAll();

        // Create receipt with known data
        // Simulate the repository saving and returning the same Receipt object
        dummyReceipt = new Receipt();
        dummyReceipt.setRetailer("Target");
        dummyReceipt.setPurchaseDate("2022-01-01");
        dummyReceipt.setPurchaseTime("13:01");
        Item item1 = new Item();
        item1.setShortDescription("Mountain Dew 12PK");
        item1.setPrice(6.49f);

        Item item2 = new Item();
        item2.setShortDescription("Emils Cheese Pizza");
        item2.setPrice(12.25f);

        Item item3 = new Item();
        item3.setShortDescription("Knorr Creamy Chicke");
        item3.setPrice(1.26f);

        Item item4 = new Item();
        item4.setShortDescription("Doritos Nacho Cheese");
        item4.setPrice(3.35f);

        Item item5 = new Item();
        item5.setShortDescription("   Klarbrunn 12-PK 12 FL OZ  ");
        item5.setPrice(12.00f);

        dummyReceipt.setItems(List.of(item1, item2, item3, item4, item5));
        dummyReceipt.setTotal(35.35f);

        items = List.of(item1, item2, item3, item4, item5);

        
    }

    @Test
    void testProcessReceipts_ReturnsSavedReceipt() throws Exception {
        String receiptJson = """
{
  "retailer": "Target",
  "purchaseDate": "2022-01-01",
  "purchaseTime": "13:01",
  "items": [
    {
      "shortDescription": "Mountain Dew 12PK",
      "price": "6.49"
    },{
      "shortDescription": "Emils Cheese Pizza",
      "price": "12.25"
    },{
      "shortDescription": "Knorr Creamy Chicken",
      "price": "1.26"
    },{
      "shortDescription": "Doritos Nacho Cheese",
      "price": "3.35"
    },{
      "shortDescription": "   Klarbrunn 12-PK 12 FL OZ  ",
      "price": "12.00"
    }
  ],
  "total": "35.35"
}
        """;

        when(receiptRepository.save(any(Receipt.class))).thenReturn(dummyReceipt);

        mockMvc.perform(post("/receipts/process")
                .contentType(MediaType.APPLICATION_JSON)
                .content(receiptJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.retailer").value("Target"))
                .andExpect(jsonPath("$.items.length()").value(5))
                .andExpect(jsonPath("$.total").value(35.35f));
    }

    // @Test
    // void testRealPointsComputation() throws Exception {

    //           // Save receipt so it has an ID
    //     dummyReceipt = receiptRepository.save(dummyReceipt);

    //   itemRepository.saveAll(items);


    //     // Save a known receipt to the DB
    //     Receipt receipt = new Receipt("Target", "2022-01-01", "13:30", ...);
    //     receipt.setItems(List.of(
    //     ...)); // known items
    //     receipt.setTotal("35.35");
    //     receipt = receiptRepository.save(receipt);

    //     // Act: call endpoint
    //     mockMvc.perform(get("/receipts/" + receipt.getId() + "/points"))
    //             .andExpect(status().isOk())
    //             .andExpect(content().string("expectedValue")); // <- actual computation happens here
    // }
}
