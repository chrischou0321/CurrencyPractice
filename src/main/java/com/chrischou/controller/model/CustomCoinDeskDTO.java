package com.chrischou.controller.model;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class CustomCoinDeskDTO {
    private String lastUpdateTime;
    private List<CustomCoinDeskData> currencyList;
}
