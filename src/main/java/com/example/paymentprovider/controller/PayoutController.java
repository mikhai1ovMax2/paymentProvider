package com.example.paymentprovider.controller;

import com.example.paymentprovider.dto.TransactionDTO;
import com.example.paymentprovider.dto.TransactionResponse;
import com.example.paymentprovider.model.TransactionType;
import com.example.paymentprovider.service.TransactionService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/")
public class PayoutController {

    private final TransactionService transactionService;

    public PayoutController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("payments/payout")
    public Mono<TransactionResponse> createTransaction(ServerWebExchange serverWebExchange, @RequestBody TransactionDTO transactionDTO) {
        return transactionService.saveTransaction(transactionDTO, serverWebExchange, TransactionType.PAYOUT);
    }

    @GetMapping("payments/payout/list")
    public Mono<Map<String, TransactionDTO>> getTransactionList(@RequestParam(required = false) LocalDateTime startDate, @RequestParam(required = false) LocalDateTime endDate) {
        if (startDate == null)
            return transactionService.getTodayTransactions(TransactionType.PAYOUT).collectList().map(transactionDTOS -> {
                        Map map = new HashMap<String, List<TransactionDTO>>();
                        map.put("transaction_list", transactionDTOS);
                        return map;
                    }
            );

        else
            return transactionService.getTransactionsByTime(startDate, endDate, TransactionType.PAYOUT).collectList().map(transactionDTOS -> {
                        Map map = new HashMap<String, List<TransactionDTO>>();
                        map.put("transaction_list", transactionDTOS);
                        return map;
                    }
            );
    }

    @GetMapping("payments/payout/{id}/details")
    public Mono<TransactionDTO> getTransactionDetails(@PathVariable long id) {
        return transactionService.getTransaction(id);
    }
}
