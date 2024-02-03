package com.example.paymentprovider.repository;

import com.example.paymentprovider.model.Merchant;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface MerchantRepository extends R2dbcRepository<Merchant, Long> {

    Mono<Merchant> findFirstBySecret(String secret);
}
