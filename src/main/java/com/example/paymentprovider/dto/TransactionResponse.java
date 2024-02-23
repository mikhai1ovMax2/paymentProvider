package com.example.paymentprovider.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransactionResponse {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long externalTransactionId;

    private String status;

    private String message;
}