package com.bank_example.transaction_service.domain.usecases;

import com.bank_example.transaction_service.domain.models.Transaction;
import reactor.core.publisher.Flux;

public interface GetTransactionsByProductIdUseCase {

    Flux<Transaction> getTransactionsByProductId(String productId);
}
