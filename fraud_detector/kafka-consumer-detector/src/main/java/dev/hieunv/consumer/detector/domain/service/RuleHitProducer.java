package dev.hieunv.consumer.detector.domain.service;

import dev.hieunv.consumer.detector.domain.event.RuleHitEvent;

public interface RuleHitProducer {

    void send(RuleHitEvent event);
}
