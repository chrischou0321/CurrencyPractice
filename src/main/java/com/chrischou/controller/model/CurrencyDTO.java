package com.chrischou.controller.model;

import com.chrischou.repository.model.CurrencyPO;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@ToString
public class CurrencyDTO {

    private long id;
    private String code;
    private String symbol;
    private BigDecimal rate;
    private String desc;

    public static CurrencyDTO fromPO(CurrencyPO po) {
        CurrencyDTO dto = new CurrencyDTO();
        dto.setId(po.getId());
        dto.setCode(po.getCode());
        dto.setSymbol(po.getSymbol());
        dto.setRate(po.getRate());
        dto.setDesc(po.getDesc() == null ? "" : po.getDesc());
        return dto;
    }

}
