package dev.hieunv.consumer.detector.api.dto;

import dev.hieunv.consumer.detector.domain.event.TransactionEvent;
import dev.ronin_engineer.kafka.common.dto.KafkaMessage;

public class TransactionMessageKafka extends KafkaMessage<TransactionEvent> {
}

