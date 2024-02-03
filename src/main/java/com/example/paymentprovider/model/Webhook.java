package com.example.paymentprovider.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Builder
@Table(schema = "payment_provider", name = "webhook")
public class Webhook {

    @Id
    @Column("id")
    private Long id;

    @Column("notification_url")
    private String notificationUrl;

    @Column("attempt")
    private int attempt;

    @Column("last_attempt_time")
    private LocalDateTime lastAttempt;

    @Column("response_code")
    private Integer responseCode;

}
