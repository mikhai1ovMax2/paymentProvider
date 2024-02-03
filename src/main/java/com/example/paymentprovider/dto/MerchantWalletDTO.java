package com.example.paymentprovider.dto;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
public class MerchantWalletDTO {


    private Long id;

    private Double amount;

    private String currency;

}
