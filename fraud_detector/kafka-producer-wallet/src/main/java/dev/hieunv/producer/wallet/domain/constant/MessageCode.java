package dev.hieunv.producer.wallet.domain.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MessageCode {

    FUND_TRANSFER("FUND_TRANSFER"),
    ;

    private final String code;
}
