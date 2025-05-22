package com.bank_example.transaction_service.infraestructure.out.persistence;

import com.bank_example.transaction_service.application.ports.out.persistence.TransactionFinderPort;
import com.bank_example.transaction_service.domain.models.Transaction;
import com.bank_example.transaction_service.infraestructure.out.persistence.mappers.TransactionPersistenceMapper;
import com.bank_example.transaction_service.infraestructure.out.persistence.repositories.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
@RequiredArgsConstructor
public class TransactionFinderAdapter implements TransactionFinderPort {

    private final TransactionRepository transactionRepository;
    private final TransactionPersistenceMapper transactionPersistenceMapper;

    @Override
    public Flux<Transaction> getTransactionsByProductId(String productId) {
        return this.transactionRepository.findByProductId(productId)
                .map(this.transactionPersistenceMapper::toTransaction);
    }
}
