package dev.hieunv.producer.wallet.domain.service;

import dev.hieunv.producer.wallet.domain.event.TransactionEvent;
import org.springframework.stereotype.Component;

@Component
public interface TransactionProducer {

    void send(TransactionEvent transactionEvent);
}
