package com.bank_example.transaction_service.infraestructure.out.http;

import com.bank_example.transaction_service.application.ports.out.http.ProductFinderPort;
import com.bank_example.transaction_service.application.ports.out.http.request.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ProductFinderAdapter implements ProductFinderPort {


    private final WebClient.Builder userWebClientBuilder;


    @Override
    public Mono<Product> findByProductId(String productId) {
        return this.userWebClientBuilder.build()
                .get()
                .uri("http://product-service/products/{productId}", productId)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> Mono.error(new Exception("Product not found")))
                .onStatus(HttpStatus::is5xxServerError, response -> Mono.error(new Exception("Server error")))
                .bodyToMono(Product.class);
    }
}
