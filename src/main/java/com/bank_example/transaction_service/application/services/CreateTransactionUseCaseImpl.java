package com.bank_example.transaction_service.application.services;

import com.bank_example.transaction_service.application.ports.out.http.ProductFinderPort;
import com.bank_example.transaction_service.application.ports.out.http.request.Product;
import com.bank_example.transaction_service.application.ports.out.persistence.TransactionCreatorPort;
import com.bank_example.transaction_service.domain.models.Transaction;
import com.bank_example.transaction_service.domain.usecases.CreateTransactionUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

@Service
@RequiredArgsConstructor
public class CreateTransactionUseCaseImpl implements CreateTransactionUseCase {

    private final TransactionCreatorPort transactionCreator;
    private final ProductFinderPort productFinder;

    @Override
    public Mono<Transaction> createTransaction(Transaction transaction) {
        return Mono.zip(
                this.productFinder.findByProductId(transaction.getSourceProductId()),
                this.productFinder.findByProductId(transaction.getProductId())
        ).flatMap(tuple -> {
            Product sourceProduct = tuple.getT1();
            Product destinationProduct = tuple.getT2();

            if (!sourceProduct.getActive()) return Mono.error(new Exception("Source product is not active"));
            if (!destinationProduct.getActive()) return Mono.error(new Exception("Destination product is not active"));
            if (transaction.getAmount().compareTo(sourceProduct.getBalance()) > 0) return Mono.error(new Exception("Source product balance is lower than destination product"));

            return Mono.zip(
                    this.transactionCreator.createIncomeTransaction(transaction),
                    this.transactionCreator.createExpenseTransaction(transaction)
            );
        }).map(Tuple2::getT1);
    }

}
