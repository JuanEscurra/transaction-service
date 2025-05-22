package com.bank_example.transaction_service.domain.usecases;

import com.bank_example.transaction_service.domain.models.Transaction;
import reactor.core.publisher.Mono;

public interface CreateTransactionUseCase {

    Mono<Transaction> createTransaction(Transaction transaction);
}
