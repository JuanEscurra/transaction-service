package com.bank_example.transaction_service.application.ports.out.persistence;

import com.bank_example.transaction_service.domain.models.Transaction;
import reactor.core.publisher.Mono;

public interface TransactionCreatorPort {

    Mono<Transaction> createIncomeTransaction(Transaction transaction);
    Mono<Transaction> createExpenseTransaction(Transaction transaction);
}
