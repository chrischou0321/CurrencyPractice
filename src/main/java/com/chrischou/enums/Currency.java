package com.chrischou.enums;

import lombok.Getter;

import java.util.stream.Stream;

@Getter
public enum Currency {
    USD("美金"),
    GBP("英鎊"),
    EUR("歐元"),
    UNKNOWN("未知幣別");

    private String codeTW;

    Currency(String codeTW) {
        this.codeTW = codeTW;
    }

    public static Currency get(String code) {
        return Stream.of(Currency.values())
                .filter(c -> c.toString().equalsIgnoreCase(code))
                .findFirst()
                .orElse(Currency.UNKNOWN);
    }
}
