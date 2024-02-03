package com.example.paymentprovider.mapper;

import com.example.paymentprovider.dto.TransactionDTO;
import com.example.paymentprovider.model.Transaction;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    TransactionDTO convert(Transaction transaction);
    Transaction convert(TransactionDTO dto);
}
