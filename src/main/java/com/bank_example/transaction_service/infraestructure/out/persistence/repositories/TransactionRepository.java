package com.bank_example.transaction_service.infraestructure.out.persistence.repositories;

import com.bank_example.transaction_service.infraestructure.out.persistence.models.TransactionDoc;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface TransactionRepository extends ReactiveMongoRepository<TransactionDoc, String> {

    Flux<TransactionDoc> findByProductId(String productId);
}
