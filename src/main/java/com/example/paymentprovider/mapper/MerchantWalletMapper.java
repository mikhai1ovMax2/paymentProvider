package com.example.paymentprovider.mapper;

import com.example.paymentprovider.dto.MerchantWalletDTO;
import com.example.paymentprovider.dto.TransactionDTO;
import com.example.paymentprovider.model.MerchantWallet;
import com.example.paymentprovider.model.Transaction;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface MerchantWalletMapper {
    MerchantWalletDTO convert(MerchantWallet transaction);
    MerchantWallet convert(MerchantWalletDTO dto);
}
