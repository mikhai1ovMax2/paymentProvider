package com.example.paymentprovider.repository;

import com.example.paymentprovider.model.Card;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface CardRepository extends R2dbcRepository<Card, Long> {

    Mono<Card> findFirstByCardNumber(String cardNumber);

    Mono<Boolean> existsByCardNumber(String cardNumber);

}
