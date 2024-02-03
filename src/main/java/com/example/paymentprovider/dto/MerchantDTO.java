package com.example.paymentprovider.dto;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
public class MerchantDTO {

    private Long id;

    private String name;

    private String secret;

    private LocalDateTime createdAt;

    private Long wallet_id;


}
