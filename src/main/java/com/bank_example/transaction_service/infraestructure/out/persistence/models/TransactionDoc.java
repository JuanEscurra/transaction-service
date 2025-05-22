package com.bank_example.transaction_service.infraestructure.out.persistence.models;

import com.bank_example.transaction_service.domain.models.TransactionType;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.Instant;

@Document(collection = "transactions")
@Data
public class TransactionDoc {

    private String id;
    private String productId;
    private BigDecimal amount;
    private TransactionType type;
    private String description;
    private Instant createdAt;
}
