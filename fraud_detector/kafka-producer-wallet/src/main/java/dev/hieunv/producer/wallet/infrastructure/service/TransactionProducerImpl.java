package dev.hieunv.producer.wallet.infrastructure.service;

import dev.hieunv.producer.wallet.domain.constant.MessageCode;
import dev.hieunv.producer.wallet.domain.event.TransactionEvent;
import dev.hieunv.producer.wallet.domain.service.TransactionProducer;
import dev.ronin_engineer.kafka.common.constant.EventType;
import dev.ronin_engineer.kafka.common.util.MessageBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@RequiredArgsConstructor
public class TransactionProducerImpl implements TransactionProducer {

    @Value("${kafka.transaction}")
    private String transactionTopic;

    @Value("${spring.application.name}")
    private String serviceId;

    private final KafkaTemplate<String, Object> kafkaTemplate;


    @Override
    public void send(TransactionEvent transaction) {
        try {
            var message = MessageBuilder.build(
                    serviceId,
                    EventType.EVENT,
                    MessageCode.FUND_TRANSFER.getCode(),
                    transaction
            );

            kafkaTemplate.send(transactionTopic, message);
            log.info("Produced a message to topic: {}, value: {}", transactionTopic, transaction);

        } catch (Exception e) {
            log.error("Failed to produce the message to topic: {}", transactionTopic);
            e.printStackTrace();
        }
    }
}
