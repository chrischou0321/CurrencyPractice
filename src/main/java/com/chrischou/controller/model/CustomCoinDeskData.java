package com.chrischou.controller.model;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@ToString
@Accessors(chain = true)
public class CustomCoinDeskData {
    private String name;
    private String nameTW;
    private BigDecimal exchangeRate;
}
