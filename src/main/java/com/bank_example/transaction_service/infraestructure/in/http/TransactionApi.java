package com.bank_example.transaction_service.infraestructure.in.http;

import com.bank_example.transaction_service.domain.usecases.CreateTransactionUseCase;
import com.bank_example.transaction_service.domain.usecases.GetTransactionsByProductIdUseCase;
import com.bank_example.transaction_service.infraestructure.in.http.api.TransactionsApiDelegate;
import com.bank_example.transaction_service.infraestructure.in.http.mappers.TransactionApiMapper;
import com.bank_example.transaction_service.infraestructure.in.http.model.TransactionRequest;
import com.bank_example.transaction_service.infraestructure.in.http.model.TransactionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.ZoneId;


@Component
@RequiredArgsConstructor
public class TransactionApi implements TransactionsApiDelegate {

    private final GetTransactionsByProductIdUseCase getTransactionsByProductId;
    private final CreateTransactionUseCase createTransaction;
    private final TransactionApiMapper mapper;

    @Override
    public Mono<ResponseEntity<TransactionResponse>> createTransaction(Mono<TransactionRequest> transactionRequest, ServerWebExchange exchange) {
        ZoneId zoneId = this.getTimezone(exchange);

        return transactionRequest
                .map(this.mapper::toTransaction)
                .flatMap(this.createTransaction::createTransaction)
                .map(transaction -> this.mapper.toTransactionResponse(transaction, zoneId))
                .map(response -> ResponseEntity.status(HttpStatus.CREATED).body(response));
    }

    @Override
    public Mono<ResponseEntity<Flux<TransactionResponse>>> getTransactionsByProductId(String productId, ServerWebExchange exchange) {
        ZoneId zoneId = this.getTimezone(exchange);

        Flux<TransactionResponse> fluxTransactions = this.getTransactionsByProductId.getTransactionsByProductId(productId)
                .map(transaction -> this.mapper.toTransactionResponse(transaction, zoneId));

        return Mono.just(ResponseEntity.ok(fluxTransactions));
    }

    private ZoneId getTimezone(ServerWebExchange exchange) {

        String timezoneHeader = exchange.getRequest().getHeaders().getFirst("X-Timezone");
        if (timezoneHeader != null) {
            return ZoneId.of(timezoneHeader);
        } else {
            return ZoneId.systemDefault();
        }
    }

}
