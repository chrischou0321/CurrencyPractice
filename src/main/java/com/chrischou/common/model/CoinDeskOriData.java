package com.chrischou.common.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.util.Map;
import java.util.Objects;

@Data
@ToString
public class CoinDeskOriData {

    private CoinDeskTimeData time;

    private String disclaimer;

    private String chartName;

    private Map<String, CoinDeskCurrencyData> bpi;

    @JsonIgnore
    public Date getLastUpdateTime() {
        if (Objects.isNull(time)) {
            return null;
        }
        if (Objects.nonNull(time.getUpdated())) {
            return time.getUpdated();
        }
        else if (Objects.nonNull(time.getUpdatedISO())) {
            return time.getUpdatedISO();
        }
        else {
            return time.getUpdateduk();
        }
    }
}
