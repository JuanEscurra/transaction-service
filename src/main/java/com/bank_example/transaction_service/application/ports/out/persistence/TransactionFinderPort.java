package com.bank_example.transaction_service.application.ports.out.persistence;

import com.bank_example.transaction_service.domain.models.Transaction;
import reactor.core.publisher.Flux;

public interface TransactionFinderPort {

    Flux<Transaction> getTransactionsByProductId(String productId);
}
