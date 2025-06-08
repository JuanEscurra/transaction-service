package com.bank_example.transaction_service.infraestructure.out.http;

import com.bank_example.transaction_service.application.ports.out.http.ProductUpdaterPort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ProductUpdaterAdapater implements ProductUpdaterPort {

    private final WebClient.Builder webClientBuilder;


    @Override
    public Mono<Void> transferMoney(
            String productOriginId,
            String productDestId,
            BigDecimal amount
    ) {

        Map<String,Object> body = new HashMap<>();
        body.put("amount",amount);
        body.put("productOriginId",productOriginId);
        body.put("productDestinationId",productDestId);

        return this.webClientBuilder.build()
                .post()
                .uri("http://product-service/products/transfer-money")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .bodyValue(body)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> Mono.error(new Exception("Error 4xx - transfer mony")))
                .onStatus(HttpStatus::is5xxServerError, response -> Mono.error(new Exception("Error 5xx - transfer money")))
                .bodyToMono(Void.class);
    }
}
