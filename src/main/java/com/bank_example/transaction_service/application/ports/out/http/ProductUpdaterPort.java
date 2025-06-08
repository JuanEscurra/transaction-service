package com.bank_example.transaction_service.application.ports.out.http;


import reactor.core.publisher.Mono;

import java.math.BigDecimal;

public interface ProductUpdaterPort {

    Mono<Void> transferMoney(String productOriginId, String productDestId, BigDecimal amount);
}
