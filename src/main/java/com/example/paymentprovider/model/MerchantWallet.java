package com.example.paymentprovider.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table(schema = "payment_provider", name = "merchant_wallet")
public class MerchantWallet {

    @Id
    @Column("id")
    private Long id;

    @Column("amount")
    private Double amount;

    @Column("currency")
    private String currency;

}
