package com.chrischou.repository.model;

import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "CURRENCY")
@Data
@Accessors(chain = true)
public class CurrencyPO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String code;
    private String symbol;
    @Column(name = "description")
    private String desc;
    private BigDecimal rate;
    private Date createTime;
    private Date updateTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CurrencyPO o1 = (CurrencyPO) o;
        return rate.compareTo(o1.rate) == 0
                && Objects.equals(id, o1.id)
                && StringUtils.equals(code, o1.code)
                && StringUtils.equals(symbol, o1.symbol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, rate, code, symbol);
    }

}