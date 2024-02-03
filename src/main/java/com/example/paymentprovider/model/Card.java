package com.example.paymentprovider.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Table(schema = "payment_provider", name = "card")
public class Card {

    @Id
    @Column("id")
    private Long id;

    @Column("card_number")
    private String cardNumber;

    @Column("exp_date")
    private LocalDate expirationDate;

    @Column("cvv")
    private String cvv;

    @Column("amount")
    private Double amount;

}
