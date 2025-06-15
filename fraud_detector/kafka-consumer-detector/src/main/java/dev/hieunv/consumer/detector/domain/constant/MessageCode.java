package dev.hieunv.consumer.detector.domain.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MessageCode {

    RULE_HIT("RULE_HIT"),
    ;

    private final String code;
}
