package com.example.paymentprovider.controller;

import com.example.paymentprovider.dto.TransactionDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.server.ServerWebExchange;

import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@WebMvcTest(PayoutController.class)
@AutoConfigureMockMvc
class PayoutControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PayoutController payoutController;





    @Test
    void createTransaction() throws Exception {

        TransactionDTO transactionDTO = new TransactionDTO();


        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/payments/payout")
                .header("Authorization", "b2eeea3e27834b7499dd7e01143a23dd")
                .content(String.valueOf(transactionDTO));
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk());

    }

    @Test
    void getTransactionList() {

    }

    @Test
    void getTransactionDetails() {

    }
}