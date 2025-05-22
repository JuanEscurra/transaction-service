package com.bank_example.transaction_service.application.services;

import com.bank_example.transaction_service.application.ports.out.persistence.TransactionFinderPort;
import com.bank_example.transaction_service.domain.models.Transaction;
import com.bank_example.transaction_service.domain.usecases.GetTransactionsByProductIdUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class GetTransactionsByProductIdUseCaseImpl implements GetTransactionsByProductIdUseCase {

    private final TransactionFinderPort transactionFinder;

    @Override
    public Flux<Transaction> getTransactionsByProductId(String productId) {
        return this.transactionFinder.getTransactionsByProductId(productId);
    }
}
