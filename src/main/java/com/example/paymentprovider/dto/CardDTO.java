package com.example.paymentprovider.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Data
public class CardDTO {

    @JsonIgnore
    private Long id;

    @JsonProperty("card_number")
    private String cardNumber;

//    @JsonProperty("card_number")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/yy")
    @JsonIgnore
    private LocalDate expirationDate;

    @JsonIgnore
    private String cvv;

    @JsonIgnore
    private Double amount;

}
