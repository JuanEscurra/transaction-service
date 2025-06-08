package com.bank_example.transaction_service.application.services;

import com.bank_example.transaction_service.application.ports.out.http.ProductFinderPort;
import com.bank_example.transaction_service.application.ports.out.http.ProductUpdaterPort;
import com.bank_example.transaction_service.application.ports.out.http.request.Product;
import com.bank_example.transaction_service.application.ports.out.persistence.TransactionCreatorPort;
import com.bank_example.transaction_service.domain.models.Transaction;
import com.bank_example.transaction_service.domain.usecases.CreateTransactionUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreateTransactionUseCaseImpl implements CreateTransactionUseCase {

    private final TransactionCreatorPort transactionCreator;
    private final ProductFinderPort productFinder;
    private final ProductUpdaterPort productUpdater;

    @Override
    public Mono<Transaction> createTransaction(Transaction transaction) {
        this.productFinder.findByProductId(transaction.getProductId())
                .subscribe(product -> System.out.println("product = " + product));

        return Mono.zip(
                this.productFinder.findByProductId(transaction.getProductId()),
                this.productFinder.findByProductId(transaction.getCounterProductId())
        ).flatMap(tuple -> {
            Product sourceProduct = tuple.getT1();
            Product destinationProduct = tuple.getT2();

            if ( Boolean.FALSE.equals(sourceProduct.getActive()) )
                return Mono.error(new Exception("Source product is not active"));
            if ( Boolean.FALSE.equals(destinationProduct.getActive()) )
                return Mono.error(new Exception("Destination product is not active"));
            if ( transaction.getAmount().compareTo(sourceProduct.getBalance()) > 0 )
                return Mono.error(new Exception("Source product balance is lower than destination product"));

            transaction.setCreatedAt(Instant.now());
            transaction.setOperationId( UUID.randomUUID().toString() );


            return Mono.zip(
                    this.transactionCreator.createIncomeTransaction(transaction),
                    this.transactionCreator.createExpenseTransaction(transaction),
                    this.productUpdater.transferMoney(
                            sourceProduct.getId(),
                            destinationProduct.getId(),
                            transaction.getAmount()
                    )
            );
        })
                .map(Tuple2::getT1)
                .doOnSuccess(response -> log.info("Created transaction: {}", response))
                .doOnError(throwable -> log.error("Error creating transaction: {}", throwable.getMessage()));
    }

}
