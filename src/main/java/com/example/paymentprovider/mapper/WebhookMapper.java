package com.example.paymentprovider.mapper;

import com.example.paymentprovider.dto.TransactionDTO;
import com.example.paymentprovider.dto.WebhookDTO;
import com.example.paymentprovider.model.Transaction;
import com.example.paymentprovider.model.Webhook;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WebhookMapper {
    WebhookDTO convert(Webhook webhook);
    Webhook convert(WebhookDTO dto);
}

