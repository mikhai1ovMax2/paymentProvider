package com.example.paymentprovider.mapper;

import com.example.paymentprovider.dto.CardDTO;
import com.example.paymentprovider.dto.CustomerDTO;
import com.example.paymentprovider.model.Card;
import com.example.paymentprovider.model.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CardMapper {
    CardDTO convert(Card merchant);
    Card convert(CardDTO dto);
}
