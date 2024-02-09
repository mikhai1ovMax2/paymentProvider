package com.example.paymentprovider.mapper;

import com.example.paymentprovider.dto.TransactionDTO;
import com.example.paymentprovider.model.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    @Mapping(target = "notificationURL", ignore = true)
    @Mapping(target = "merchantDTO", ignore = true)
    @Mapping(target = "customerDTO", ignore = true)
    @Mapping(target = "webhookDTO", ignore = true)
    @Mapping(target = "cardDTO", ignore = true)
    TransactionDTO convert(Transaction transaction);
    Transaction convert(TransactionDTO dto);
}
