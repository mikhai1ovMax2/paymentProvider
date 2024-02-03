package com.example.paymentprovider.controller;

import com.example.paymentprovider.dto.TransactionDTO;
import com.example.paymentprovider.dto.TransactionResponse;
import com.example.paymentprovider.model.TransactionType;
import com.example.paymentprovider.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("transaction")
    public Mono<TransactionResponse> createTransaction(ServerWebExchange serverWebExchange, @RequestBody TransactionDTO transactionDTO) {
        return transactionService.saveTransaction(transactionDTO, serverWebExchange, TransactionType.PAYMENT);
//                .onErrorReturn(TransactionResponse.builder().status("FAILED").message("PAYMENT_METHOD_NOT_ALLOWED").build());
    }

    @GetMapping("payments/transaction/list")
    public Mono<Map<String, TransactionDTO>> getTransactionList(@RequestParam(required = false) LocalDateTime startDate, @RequestParam(required = false) LocalDateTime endDate) {
        if (startDate == null)
            return transactionService.getTodayTransactions(TransactionType.PAYMENT).collectList().map(transactionDTOS -> {
                        Map map = new HashMap<String, List<TransactionDTO>>();
                        map.put("transaction_list", transactionDTOS);
                        return map;
                    }
            );

        else
            return transactionService.getTransactionsByTime(startDate, endDate, TransactionType.PAYMENT).collectList().map(transactionDTOS -> {
                        Map map = new HashMap<String, List<TransactionDTO>>();
                        map.put("transaction_list", transactionDTOS);
                        return map;
                    }
            );
    }

    @GetMapping("payments/transaction/{id}/details")
    public Mono<TransactionDTO> getTransactionDetails(@PathVariable long id){
        return transactionService.getTransaction(id);
    }
}
