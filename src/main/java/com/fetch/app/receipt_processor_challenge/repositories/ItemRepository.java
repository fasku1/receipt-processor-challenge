package com.fetch.app.receipt_processor_challenge.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.fetch.app.receipt_processor_challenge.entities.Item;

public interface ItemRepository extends JpaRepository<Item, Long>{
}
