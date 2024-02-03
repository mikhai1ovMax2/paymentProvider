package com.example.paymentprovider.mapper;

import com.example.paymentprovider.dto.CustomerDTO;
import com.example.paymentprovider.dto.MerchantDTO;
import com.example.paymentprovider.model.Customer;
import com.example.paymentprovider.model.Merchant;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerDTO convert(Customer merchant);
    Customer convert(CustomerDTO dto);
}