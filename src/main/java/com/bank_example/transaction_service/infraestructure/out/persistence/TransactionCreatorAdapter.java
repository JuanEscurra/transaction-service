package com.bank_example.transaction_service.infraestructure.out.persistence;

import com.bank_example.transaction_service.application.ports.out.persistence.TransactionCreatorPort;
import com.bank_example.transaction_service.domain.models.Transaction;
import com.bank_example.transaction_service.infraestructure.out.persistence.mappers.TransactionPersistenceMapper;
import com.bank_example.transaction_service.infraestructure.out.persistence.models.TransactionDoc;
import com.bank_example.transaction_service.infraestructure.out.persistence.repositories.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class TransactionCreatorAdapter implements TransactionCreatorPort {

    private final TransactionRepository repository;
    private final TransactionPersistenceMapper mapper;

    @Override
    public Mono<Transaction> createIncomeTransaction(Transaction transaction) {
        String productId = transaction.getCounterProductId();

        TransactionDoc transactionDoc = this.mapper.toTransactionDoc(transaction);
        transactionDoc.setCounterProductId( transaction.getProductId());
        transactionDoc.setProductId( productId);

        return this.repository.save(transactionDoc)
                .map(mapper::toTransaction);
    }

    @Override
    public Mono<Transaction> createExpenseTransaction(Transaction transaction) {
        TransactionDoc transactionDoc = this.mapper.toTransactionDoc(transaction);
        transactionDoc.setAmount(transaction.getAmount().negate());
        return this.repository.save(transactionDoc)
                .map(mapper::toTransaction);
    }

}
