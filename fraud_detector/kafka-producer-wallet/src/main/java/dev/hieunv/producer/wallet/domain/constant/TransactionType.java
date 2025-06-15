package dev.hieunv.producer.wallet.domain.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TransactionType {

    PAYMENT("PAYMENT"),
    TOPUP("TOPUP"),
    ;

    private final String type;
}
