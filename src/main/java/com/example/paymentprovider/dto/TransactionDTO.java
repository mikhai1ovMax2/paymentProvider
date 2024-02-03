package com.example.paymentprovider.dto;

import com.example.paymentprovider.model.Merchant;
import com.example.paymentprovider.model.TransactionStatus;
import com.example.paymentprovider.model.TransactionType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
public class TransactionDTO {

    private Long id;

    @JsonProperty("payment_method")
    private String paymentMethod;

    private String currency;

    private Double amount;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String language;

    @JsonProperty("notification_url")
    private String notificationURL;

    private TransactionStatus status;

    @JsonIgnore
    private MerchantDTO merchantDTO;

    @JsonProperty("customer")
    private CustomerDTO customerDTO;

    @JsonIgnore
    private WebhookDTO webhookDTO;

    @JsonProperty("card_data")
    private CardDTO cardDTO;

    @JsonIgnore
    private Long merchantId;

    @JsonIgnore
    private Long clientId;

    @JsonIgnore
    private Long webhookId;

    @JsonIgnore
    private Long cardId;

    @JsonIgnore
    private TransactionType transactionType;
}
