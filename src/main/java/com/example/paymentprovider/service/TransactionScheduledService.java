package com.example.paymentprovider.service;

import com.example.paymentprovider.exception.InvalidDataException;
import com.example.paymentprovider.model.Transaction;
import com.example.paymentprovider.model.TransactionStatus;
import com.example.paymentprovider.model.TransactionType;
import com.example.paymentprovider.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
@RequiredArgsConstructor
public class TransactionScheduledService {

    private final TransactionRepository transactionRepository;
    private final CardRepository cardRepository;
    private final MerchantWalletRepository merchantWalletRepository;
    private final MerchantRepository merchantRepository;
    private final WebhookRepository webhookRepository;
    private final NotificationService notificationService;

    @Scheduled(fixedDelay = 10 * 1000)
    private Mono<Void> doPayment() {
        return transactionRepository.findAllByStatus(TransactionStatus.IN_PROCESS)
                .flatMap(transaction -> {
                    return cardRepository.findById(transaction.getCardId())
                            .flatMap(card -> {
                                return merchantRepository.findById(transaction.getMerchantId())
                                        .flatMap(merchant -> {
                                            return merchantWalletRepository.findById(merchant.getWallet_id())
                                                    .flatMap(merchantWallet -> {
                                                        if (transaction.getTransactionType().equals(TransactionType.PAYMENT)) {

                                                            if (card.getAmount() < transaction.getAmount()) {
                                                                transaction.setStatus(TransactionStatus.FAILED);
                                                                transactionRepository.save(transaction).subscribe();
                                                                return notificationService.sendTransactionNotification(transaction, "NOT_ENOUGH_MONEY", TransactionStatus.FAILED);
                                                            } else if ((int) (Math.random() * 5) == 1) {
                                                                transaction.setStatus(TransactionStatus.FAILED);
                                                                transactionRepository.save(transaction).subscribe();
                                                                return notificationService.sendTransactionNotification(transaction, "INTERNAL_ERROR", TransactionStatus.FAILED);
                                                            } else {
                                                                card.setAmount(card.getAmount() - transaction.getAmount());
                                                                cardRepository.save(card).subscribe();
                                                                merchantWallet.setAmount(merchantWallet.getAmount() + transaction.getAmount());
                                                                merchantWalletRepository.save(merchantWallet).subscribe();
                                                                transaction.setStatus(TransactionStatus.APPROVED);
                                                                transactionRepository.save(transaction).subscribe();
                                                                return notificationService.sendTransactionNotification(transaction, "OK", TransactionStatus.APPROVED);
                                                            }
                                                        } else {
                                                            if (merchantWallet.getAmount() < transaction.getAmount()) {
                                                                transaction.setStatus(TransactionStatus.FAILED);
                                                                transactionRepository.save(transaction).subscribe();
                                                                return notificationService.sendTransactionNotification(transaction, "NOT_ENOUGH_MONEY", TransactionStatus.FAILED);
                                                            } else if ((int) (Math.random() * 5) == 1) {
                                                                transaction.setStatus(TransactionStatus.FAILED);
                                                                transactionRepository.save(transaction).subscribe();
                                                                return notificationService.sendTransactionNotification(transaction, "INTERNAL_ERROR", TransactionStatus.FAILED);
                                                            } else {
                                                                card.setAmount(card.getAmount() + transaction.getAmount());
                                                                cardRepository.save(card).subscribe();
                                                                merchantWallet.setAmount(merchantWallet.getAmount() - transaction.getAmount());
                                                                merchantWalletRepository.save(merchantWallet).subscribe();
                                                                transaction.setStatus(TransactionStatus.APPROVED);
                                                                transactionRepository.save(transaction).subscribe();
                                                                notificationService.sendTransactionNotification(transaction, "OK", TransactionStatus.APPROVED).subscribe();
                                                            }
                                                            return Mono.empty();
                                                        }
                                                    });
                                        });
                            });
                }).then();
    }
}
