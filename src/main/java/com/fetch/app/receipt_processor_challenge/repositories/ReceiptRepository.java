package com.fetch.app.receipt_processor_challenge.repositories;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fetch.app.receipt_processor_challenge.entities.Receipt;

public interface ReceiptRepository extends JpaRepository<Receipt, UUID>{

}
