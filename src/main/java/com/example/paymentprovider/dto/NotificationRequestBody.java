package com.example.paymentprovider.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class NotificationRequestBody {

    @JsonProperty("transaction_id")
    private Long transactionId;

    private String status;

    private String message;
}
