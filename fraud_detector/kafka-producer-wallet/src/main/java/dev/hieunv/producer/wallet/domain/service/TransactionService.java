package dev.hieunv.producer.wallet.domain.service;


import dev.hieunv.producer.wallet.domain.constant.TransactionStatus;
import dev.hieunv.producer.wallet.domain.constant.TransactionType;
import dev.hieunv.producer.wallet.domain.dto.TransactionRequest;
import dev.hieunv.producer.wallet.domain.event.TransactionEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionProducer transactionProducer;

    public TransactionEvent execute(TransactionRequest request) {
        var transactionId = UUID.randomUUID().toString();

        var transaction = TransactionEvent.builder()
                .transactionId(transactionId)
                .transactionType(TransactionType.PAYMENT.getType())
                .debitAccount(request.getDebitAccount())
                .creditAccount(request.getCreditAccount())
                .amount(request.getAmount())
                .status(TransactionStatus.SUCCESSFUL.getStatus())
                .createdAt(System.currentTimeMillis())
                .build();

        transactionProducer.send(transaction);

        return transaction;
    }

}
