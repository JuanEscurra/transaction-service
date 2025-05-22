package com.bank_example.transaction_service.infraestructure.out.persistence.mappers;

import com.bank_example.transaction_service.domain.models.Transaction;
import com.bank_example.transaction_service.infraestructure.out.persistence.models.TransactionDoc;
import org.springframework.stereotype.Component;

@Component
public class TransactionPersistenceMapper {

    public Transaction toTransaction(TransactionDoc transactionDoc) {
        Transaction transaction = new Transaction();
        transaction.setId(transactionDoc.getId());
        transaction.setAmount(transactionDoc.getAmount());
        transaction.setDescription(transactionDoc.getDescription());
        transaction.setType(transactionDoc.getType());
        return transaction;
    }
}
