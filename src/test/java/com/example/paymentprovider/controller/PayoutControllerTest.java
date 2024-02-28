package com.example.paymentprovider.controller;

import com.example.paymentprovider.dto.CardDTO;
import com.example.paymentprovider.dto.CustomerDTO;
import com.example.paymentprovider.dto.TransactionDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.client.MockMvcWebTestClient;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.HashMap;

import static com.fasterxml.jackson.databind.type.LogicalType.Map;
import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


//@SpringBootTest(properties = "spring.main.web-application-type=reactive")
@AutoConfigureMockMvc
@SpringBootTest
class PayoutControllerTest {


    @Autowired
    WebTestClient mockMvc;


    TransactionDTO transactionDTO = TransactionDTO.builder()
            .paymentMethod("CARD")
            .amount(123.1)
            .currency("BRL")
            .language("RU")
            .notificationURL("http://localhost:8088/resp200")
            .customerDTO(CustomerDTO.builder()
                    .firstName("Ivan")
                    .lastName("Ivanov")
                    .country("Peru")
                    .build())
            .cardDTO(CardDTO.builder()
                    .cardNumber("4102778822334893")
                    .expirationDate(LocalDate.of(2025, 11, 11))
                    .cvv("566")
                    .build())
            .build();

    @Test
    void createTransaction() throws Exception {



        mockMvc.post()
                .uri("/api/v1/payments/payout")
                .header("Authorization","b2eeea3e27834b7499dd7e01143a23dd")
                .body(Mono.just(transactionDTO), TransactionDTO.class)
                .exchange()
                .expectStatus().is2xxSuccessful();

    }

//    @Test
//    void getTransactionList() {
//        mockMvc.get()
//                .uri("/api/v1/payments/payout/list")
//                .header("Authorization","b2eeea3e27834b7499dd7e01143a23dd")
//                .exchange()
//                .expectStatus().is2xxSuccessful();
////                .expectBody(Map.getClass())
////                .isEqualTo(Mono.just(new HashMap<String, TransactionDTO>()));
//    }

    @Test
    void getTransactionDetails() {
        mockMvc.get()
                .uri("/api/v1/payments/transaction/21/details")
                .header("Authorization","b2eeea3e27834b7499dd7e01143a23dd")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody().json("{\"id\": 21, \"currency\": \"BRL\", \"amount\": 1000.0, \"createdAt\": \"2024-01-25T09:12:34.413\", \"updatedAt\": \"2024-01-25T09:12:34.413\", \"language\": \"en\", \"status\": \"FAILED\", \"payment_method\": \"CARD\", \"notification_url\": null, \"customer\": null, \"card_data\": null}", false);
    }
}