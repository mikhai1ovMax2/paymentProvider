package com.example.paymentprovider.mapper;

import com.example.paymentprovider.dto.MerchantDTO;
import com.example.paymentprovider.dto.MerchantWalletDTO;
import com.example.paymentprovider.model.Merchant;
import com.example.paymentprovider.model.MerchantWallet;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface MerchantMapper {
    MerchantDTO convert(Merchant merchant);
    Merchant convert(MerchantDTO dto);
}

