package com.chrischou.controller.request;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@ToString
public class CurrencyUpdateRequest {

    @NotBlank
    private String code;
    @NotBlank
    private String symbol;
    @NotNull
    private BigDecimal rate;
    private String desc;

    public CurrencyUpdateRequest(){}

    public CurrencyUpdateRequest(String code, String symbol, BigDecimal rate, String desc) {
        this.code = code;
        this.symbol = symbol;
        this.rate = rate;
        this.desc = desc;
    }
}
