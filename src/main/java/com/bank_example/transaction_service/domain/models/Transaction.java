package com.bank_example.transaction_service.domain.models;

import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
public class Transaction {

    private String id;
    private String productId;
    private String sourceProductId;
    private BigDecimal amount;
    private TransactionType type;
    private String description;
    private Instant createdAt;


}
