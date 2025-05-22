package com.bank_example.transaction_service.infraestructure.in.http;

import com.bank_example.transaction_service.domain.usecases.GetTransactionsByProductIdUseCase;
import com.bank_example.transaction_service.infraestructure.in.http.api.TransactionsApiDelegate;
import com.bank_example.transaction_service.infraestructure.in.http.mappers.TransactionApiMapper;
import com.bank_example.transaction_service.infraestructure.in.http.model.TransactionRequest;
import com.bank_example.transaction_service.infraestructure.in.http.model.TransactionResponse;
import lombok.RequiredArgsConstructor;
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
    private final TransactionApiMapper transactionApiMapper;

    /* PENDIENTE POR FINALIZAR */
    @Override
    public Mono<ResponseEntity<TransactionResponse>> createTransaction(Mono<TransactionRequest> transactionRequest, ServerWebExchange exchange) {

        return TransactionsApiDelegate.super.createTransaction(transactionRequest, exchange);
    }

    @Override
    public Mono<ResponseEntity<Flux<TransactionResponse>>> getTransactionsByProductId(String productId, ServerWebExchange exchange) {
        ZoneId zoneId = this.getTimezone(exchange);

        Flux<TransactionResponse> fluxTransactions = this.getTransactionsByProductId.getTransactionsByProductId(productId)
                .map(transaction -> this.transactionApiMapper.toTransactionResponse(transaction, zoneId));

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
