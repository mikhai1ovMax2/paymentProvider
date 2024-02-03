package com.example.paymentprovider.service;

//import com.example.paymentprovider.dto.NotificationRequestBody;

import com.example.paymentprovider.dto.NotificationRequestBody;
import com.example.paymentprovider.model.Transaction;
import com.example.paymentprovider.model.TransactionStatus;
import com.example.paymentprovider.repository.WebhookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final WebhookRepository webhookRepository;

    //TODO повторная отправка сообщений
    public Mono<Void> sendTransactionNotification(Transaction transaction, String message, TransactionStatus status) {

        webhookRepository.findById(transaction.getWebhookId())
                .flatMap(webhook -> {
                    WebClient client = WebClient.create();

                    try {
                        return client
                                .post()
                                .uri(new URI(webhook.getNotificationUrl()))
                                .body(BodyInserters.fromValue(NotificationRequestBody.builder().transactionId(transaction.getId()).message(message).status(status.toString()).build()))
                                .retrieve()
                                .toEntity(String.class)
                                .flatMap(stringResponseEntity -> {
                                    webhook.setResponseCode(stringResponseEntity.getStatusCode().value());
                                    webhook.setAttempt(webhook.getAttempt()+1);
                                    webhook.setLastAttempt(LocalDateTime.now());
                                    webhookRepository.save(webhook).subscribe();
                                    return Mono.empty();
                                });
                    } catch (URISyntaxException e) {
                        return Mono.error(new RuntimeException(e));
                    }

                }).subscribe();


        return Mono.empty();
    }
}
