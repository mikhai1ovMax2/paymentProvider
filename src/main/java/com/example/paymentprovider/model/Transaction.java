package com.example.paymentprovider.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Table(schema = "payment_provider_reserve", name = "transaction")
public class Transaction {

    @Id
    @Column("id")
    private Long id;

    @Column("payment_method")
    private String paymentMethod;

    @Column("currency")
    private String currency;

    @Column("amount")
    private Double amount;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;

    @Column("language")
    private String language;

    @Column("status")
    private TransactionStatus status;

    @Column("merchant_id")
    private Long merchantId;

    @Column("customer_id")
    private Long clientId;

    @Column("webhook_id")
    private Long webhookId;

    @Column("card_id")
    private Long cardId;

    @Column("type")
    private TransactionType transactionType;

}
