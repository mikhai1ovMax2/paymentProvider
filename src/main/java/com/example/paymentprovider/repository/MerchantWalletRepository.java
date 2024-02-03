package com.example.paymentprovider.repository;

import com.example.paymentprovider.model.MerchantWallet;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface MerchantWalletRepository extends R2dbcRepository<MerchantWallet, Long> {
}
