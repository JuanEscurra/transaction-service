package com.bank_example.transaction_service.infraestructure.in.http.mappers;

import com.bank_example.transaction_service.domain.models.Transaction;
import com.bank_example.transaction_service.infraestructure.in.http.model.TransactionResponse;
import org.springframework.stereotype.Component;

import java.time.ZoneId;

@Component
public class TransactionApiMapper {

    public TransactionResponse toTransactionResponse(Transaction transaction, ZoneId zoneId) {
        TransactionResponse transactionResponse = new TransactionResponse();
        transactionResponse.setId(transaction.getId());
        transactionResponse.setAmount(transaction.getAmount().doubleValue());
        transactionResponse.setDescription(transaction.getDescription());
        transactionResponse.setCreatedAt(transaction.getCreatedAt().atZone(zoneId).toOffsetDateTime());
        return transactionResponse;
    }
}
