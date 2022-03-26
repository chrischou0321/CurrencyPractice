package com.chrischou.controller.request;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@ToString
public class CurrencyAddRequest {

    @NotBlank(message = "code cannot be blank.")
    private String code;
    @NotBlank(message = "symbol cannot be blank.")
    private String symbol;
    @NotNull(message = "rate must not be null.")
    private BigDecimal rate;
    private String desc;

    public CurrencyAddRequest(){}

    public CurrencyAddRequest(String code, String symbol, BigDecimal rate, String desc) {
        this.code = code;
        this.symbol = symbol;
        this.rate = rate;
        this.desc = desc;
    }
}
