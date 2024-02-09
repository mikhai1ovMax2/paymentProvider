package com.example.paymentprovider.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Table(schema = "payment_provider", name = "merchant")

public class Merchant {

    @Id
    @Column("id")
    private Long id;

    @Column("name")
    private String name;

    @Column("secret")
    private String secret;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;

    @Column("wallet_id")
    private Long wallet_id;


}
