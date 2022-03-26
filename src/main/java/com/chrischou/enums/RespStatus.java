package com.chrischou.enums;

import lombok.Getter;

import java.util.stream.Stream;

@Getter
public enum RespStatus {
    SUCCESS(111, ""),
    INVALID(222, "The parameters are invalid."),
    ERROR(333, "System error occurred."),
    UNKNOWN(999, "Unknown error");

    private final int code;
    private final String desc;

    RespStatus(int code, String defaultDesc) {
        this.code = code;
        this.desc = defaultDesc;
    }

    public static RespStatus get(int code) {
        return Stream.of(RespStatus.values()).filter(s -> s.getCode() == code).findFirst().orElse(RespStatus.UNKNOWN);
    }
}
