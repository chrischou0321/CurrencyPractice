package com.chrischou.common.model;

import com.chrischou.common.parser.DateGMTParser;
import com.chrischou.common.parser.DateISOParser;
import com.chrischou.common.parser.DateUTCParser;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import java.util.Date;

@Data
public class CoinDeskTimeData {
    @JsonDeserialize(using = DateUTCParser.class)
    private Date updated;
    @JsonDeserialize(using = DateISOParser.class)
    private Date updatedISO;
    @JsonDeserialize(using = DateGMTParser.class)
    private Date updateduk;
}
