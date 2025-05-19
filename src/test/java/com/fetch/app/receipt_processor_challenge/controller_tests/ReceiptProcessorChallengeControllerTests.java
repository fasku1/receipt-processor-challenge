package com.fetch.app.receipt_processor_challenge.controller_tests;

import java.util.List;
import java.util.Optional;

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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReceiptProcessorChallengeController.class)
class ReceiptControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReceiptRepository receiptRepository;

    @MockBean
    private ItemRepository itemRepository;

    private Receipt receipt;

    private List<Item> items;

    /**
     * Creates data used for tests
     */
    @BeforeEach
    void setUp() {
        // Create receipt with known data
        // Simulate the repository saving and returning the same Receipt object
        receipt = new Receipt();
        receipt.setRetailer("Target");
        receipt.setPurchaseDate("2022-01-01");
        receipt.setPurchaseTime("13:01");
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

        receipt.setItems(List.of(item1, item2, item3, item4, item5));
        receipt.setTotal(35.35f);

        items = List.of(item1, item2, item3, item4, item5);

        receipt.setId("temp");

        when(receiptRepository.save(any(Receipt.class))).thenAnswer(invocation -> {
            Receipt receipt = invocation.getArgument(0);
            if (receipt.getId() == null) {
                receipt.setId("temp");
            }
            return receipt;
        });

        when(receiptRepository.findById("temp")).thenReturn(Optional.of(receipt));

        // **Add this to mock items returned by item repository**
        when(itemRepository.findByReceiptId("temp")).thenReturn(items);
    }

    /**
     * Test case for posting a receipt endpoint.
     */
    @Test
    void testProcessReceipts() throws Exception {
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

        when(receiptRepository.save(any(Receipt.class))).thenReturn(receipt);

        mockMvc.perform(post("/receipts/process")
                .contentType(MediaType.APPLICATION_JSON)
                .content(receiptJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.retailer").value("Target"))
                .andExpect(jsonPath("$.items.length()").value(5))
                .andExpect(jsonPath("$.total").value(35.35f));
    }

    /**
     * Test case for the get points endpoint.
     */
    @Test
    void testRealPointsComputation() throws Exception {
        receipt = receiptRepository.save(receipt);
        itemRepository.saveAll(items);
        mockMvc.perform(get("/receipts/temp/points"))
                .andExpect(status().isOk())
                .andExpect(content().string("28"));
    }
}
