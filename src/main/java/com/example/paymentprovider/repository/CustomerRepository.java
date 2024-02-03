package com.example.paymentprovider.repository;

import com.example.paymentprovider.model.Customer;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface CustomerRepository extends R2dbcRepository<Customer, Long> {
        }
