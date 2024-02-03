package com.example.paymentprovider.service;

import com.example.paymentprovider.dto.TransactionDTO;
import com.example.paymentprovider.dto.TransactionResponse;
import com.example.paymentprovider.mapper.CardMapper;
import com.example.paymentprovider.mapper.CustomerMapper;
import com.example.paymentprovider.mapper.TransactionMapper;
import com.example.paymentprovider.model.Transaction;
import com.example.paymentprovider.model.TransactionStatus;
import com.example.paymentprovider.model.TransactionType;
import com.example.paymentprovider.model.Webhook;
import com.example.paymentprovider.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;

    private final TransactionMapper transactionMapper;

    private final CardRepository cardRepository;

    private final MerchantRepository merchantRepository;

    private final WebhookRepository webhookRepository;

    private final CustomerRepository customerRepository;

    private final CustomerMapper customerMapper;

    private final CardMapper cardMapper;


    public Mono<TransactionResponse> saveTransaction(TransactionDTO transactionDTO, ServerWebExchange serverWebExchange, TransactionType transactionType) {
        transactionDTO.setStatus(TransactionStatus.IN_PROCESS);
        transactionDTO.setTransactionType(transactionType);
        return cardRepository.findFirstByCardNumber(transactionDTO.getCardDTO().getCardNumber())
                .flatMap(card -> {
                            transactionDTO.setCardId(card.getId());
//                            return Mono.just(transactionDTO);
                            return merchantRepository.findFirstBySecret(serverWebExchange.getRequest().getHeaders().getFirst("Authorization"))
                                    .flatMap(merchant -> {
                                        transactionDTO.setMerchantId(merchant.getId());
                                        return webhookRepository.save(Webhook.builder().notificationUrl(transactionDTO.getNotificationURL()).attempt(0).build())
                                                .flatMap(webhook -> {
                                                    transactionDTO.setWebhookId(webhook.getId());
                                                    return transactionRepository.save(transactionMapper.convert(transactionDTO))
                                                            .map(transaction -> TransactionResponse.builder().externalTransactionId(transaction.getId()).status("APPROVED").message("OK").build());
                                                });

                                    });
                        }
                );
    }

    public Flux<TransactionDTO> getTodayTransactions(TransactionType transactionType) {
        return transactionRepository.findAllByCreatedAtAfterAndTransactionType(LocalDate.now().atStartOfDay(), transactionType)
                .map(transactionMapper::convert)
                .flatMap(transactionDTO -> {
                            return customerRepository.findById(transactionDTO.getClientId()).flatMap(customer -> {
                                transactionDTO.setCustomerDTO(customerMapper.convert(customer));
                                return Mono.just(transactionDTO);
                            });
                        }
                )
                .flatMap(transactionDTO -> {
                    return cardRepository.findById(transactionDTO.getCardId()).flatMap(card -> {
                        transactionDTO.setCardDTO(cardMapper.convert(card));
                        return Mono.just(transactionDTO);
                    });
                })
                .flatMap(transactionDTO -> {
                    return webhookRepository.findById(transactionDTO.getWebhookId()).flatMap(webhook -> {
                        transactionDTO.setNotificationURL(webhook.getNotificationUrl());
                        return Mono.just(transactionDTO);
                    });
                });
    }


    public Flux<TransactionDTO> getTransactionsByTime(LocalDateTime startAt, LocalDateTime endTime, TransactionType transactionType) {
        return transactionRepository.findAllByCreatedAtAfterAndCreatedAtBeforeAndTransactionType(startAt, endTime, transactionType)
                .map(transactionMapper::convert)
                .flatMap(transactionDTO -> {
                            return customerRepository.findById(transactionDTO.getClientId()).flatMap(customer -> {
                                transactionDTO.setCustomerDTO(customerMapper.convert(customer));
                                return Mono.just(transactionDTO);
                            });
                        }
                )
                .flatMap(transactionDTO -> {
                    return cardRepository.findById(transactionDTO.getCardId()).flatMap(card -> {
                        transactionDTO.setCardDTO(cardMapper.convert(card));
                        return Mono.just(transactionDTO);
                    });
                })
                .flatMap(transactionDTO -> {
                    return webhookRepository.findById(transactionDTO.getWebhookId()).flatMap(webhook -> {
                        transactionDTO.setNotificationURL(webhook.getNotificationUrl());
                        return Mono.just(transactionDTO);
                    });
                });
    }

    public Mono<TransactionDTO> getTransaction(long id) {
        return transactionRepository.findById(id).map(transactionMapper::convert);
    }
}
