package com.fetch.app.receipt_processor_challenge.repositories;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fetch.app.receipt_processor_challenge.entities.Item;

public interface ItemRepository extends JpaRepository<Item, Long>{
        List<Item> findByReceiptId(UUID receipt_Id);
}
