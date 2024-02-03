package com.example.paymentprovider.repository;

import com.example.paymentprovider.model.Transaction;
import com.example.paymentprovider.model.TransactionStatus;
import com.example.paymentprovider.model.TransactionType;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

public interface TransactionRepository extends R2dbcRepository<Transaction, Long> {

    Flux<Transaction> findAllByStatus(TransactionStatus status);

    Flux<Transaction> findAllByCreatedAtAfterAndTransactionType(LocalDateTime createdAt, TransactionType transactionType);

    Flux<Transaction> findAllByCreatedAtAfterAndCreatedAtBeforeAndTransactionType(LocalDateTime startDate, LocalDateTime endDate, TransactionType transactionType);

}
