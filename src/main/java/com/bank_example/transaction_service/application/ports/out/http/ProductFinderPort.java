package com.bank_example.transaction_service.application.ports.out.http;

import com.bank_example.transaction_service.application.ports.out.http.request.Product;
import reactor.core.publisher.Mono;

public interface ProductFinderPort {

    Mono<Product> findByProductId(String productId);
}
