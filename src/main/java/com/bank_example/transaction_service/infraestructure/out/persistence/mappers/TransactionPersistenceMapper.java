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
        transaction.setCreatedAt(transactionDoc.getCreatedAt());
        transaction.setProductId(transactionDoc.getProductId());
        transaction.setCounterProductId(transactionDoc.getCounterProductId());
        transaction.setOperationId(transactionDoc.getOperationId());

        return transaction;
    }

    public TransactionDoc toTransactionDoc(Transaction transaction) {
        TransactionDoc transactionDoc = new TransactionDoc();
        transactionDoc.setId(transaction.getId());
        transactionDoc.setAmount(transaction.getAmount());
        transactionDoc.setDescription(transaction.getDescription());
        transactionDoc.setType(transaction.getType());
        transactionDoc.setCreatedAt(transaction.getCreatedAt());
        transactionDoc.setOperationId(transaction.getOperationId());
        transactionDoc.setProductId(transaction.getProductId());
        transactionDoc.setCounterProductId(transaction.getCounterProductId());

        return transactionDoc;
    }
}
