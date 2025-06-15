package dev.hieunv.consumer.detector.api.kafka;

import dev.hieunv.consumer.detector.api.dto.TransactionMessageKafka;
import dev.hieunv.consumer.detector.domain.service.RuleEngine;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.RetriableException;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.DltStrategy;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaConsumer {

    private final RuleEngine ruleEngine;

    @RetryableTopic(
            attempts = "3",
            backoff = @Backoff(delay = 1000, multiplier = 2.0),
            dltStrategy = DltStrategy.FAIL_ON_ERROR,
            autoCreateTopics = "true", // false: commended
            include = {RetriableException.class}
    )
    @KafkaListener(
            topics = "${kafka.transaction.topic}",
            properties = {"spring.json.value.default.type=dev.hieunv.consumer.detector.api.dto.TransactionMessageKafka"},
            concurrency = "3"
    )
    @SneakyThrows
    public void listenTransaction(@Payload TransactionMessageKafka message) {
        log.info("Received a transaction message - code: {} - meta: {} - payload: {}", message.getMessageCode(), message.getMeta(), message.getPayload());
        ruleEngine.process(message.getPayload());
    }

    @DltHandler
    public void retryTransactions(@Payload TransactionMessageKafka message) {
        log.warn("Retry transaction message: {}", message);
        // TODO: implement retry logic
    }
}
