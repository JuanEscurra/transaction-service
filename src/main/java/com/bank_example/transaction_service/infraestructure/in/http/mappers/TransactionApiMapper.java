package com.bank_example.transaction_service.infraestructure.in.http.mappers;

import com.bank_example.transaction_service.domain.models.Transaction;
import com.bank_example.transaction_service.infraestructure.in.http.model.TransactionRequest;
import com.bank_example.transaction_service.infraestructure.in.http.model.TransactionResponse;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.ZoneId;

@Component
public class TransactionApiMapper {

    public TransactionResponse toTransactionResponse(Transaction transaction, ZoneId zoneId) {
        TransactionResponse transactionResponse = new TransactionResponse();
        transactionResponse.setId(transaction.getId());
        transactionResponse.setAmount(transaction.getAmount().doubleValue());
        transactionResponse.setDescription(transaction.getDescription());
        transactionResponse.setCreatedAt(transaction.getCreatedAt().atZone(zoneId).toOffsetDateTime());
        transactionResponse.setProductId(transaction.getProductId());

        return transactionResponse;
    }


    public Transaction toTransaction(TransactionRequest request) {
        Transaction transaction = new Transaction();
        transaction.setProductId(request.getProductId());
        transaction.setCounterProductId(request.getCounterProductId());
        transaction.setAmount(BigDecimal.valueOf(request.getAmount()));
        transaction.setDescription(request.getDescription());

        return transaction;
    }

}
