package com.bank_example.transaction_service.infraestructure.out.persistence;

import com.bank_example.transaction_service.application.ports.out.persistence.TransactionCreatorPort;
import com.bank_example.transaction_service.domain.models.Transaction;
import com.bank_example.transaction_service.infraestructure.out.persistence.mappers.TransactionPersistenceMapper;
import com.bank_example.transaction_service.infraestructure.out.persistence.repositories.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class TransactionCreatorAdapter implements TransactionCreatorPort {

    private final TransactionRepository transactionRepository;
    private final TransactionPersistenceMapper transactionPersistenceMapper;

    @Override
    public Mono<Transaction> createIncomeTransaction(Transaction transaction) {

        return null;
    }

    @Override
    public Mono<Transaction> createExpenseTransaction(Transaction transaction) {
        return null;
    }


}
