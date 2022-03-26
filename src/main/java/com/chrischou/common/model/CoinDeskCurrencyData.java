package com.chrischou.common.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CoinDeskCurrencyData {

    private String code;
    private String symbol;
    private String rate;
    @JsonProperty("description")
    private String desc;
    @JsonProperty("rate_float")
    private BigDecimal rateFloat;
}
