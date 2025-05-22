package com.bank_example.transaction_service.application.ports.out.http.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Product {

    private String id;
    private String accountNumber;
    private BigDecimal balance;
    private ProductType productType;
    private Boolean active;

}
