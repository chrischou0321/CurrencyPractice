package com.chrischou.common.parser;

import com.chrischou.common.utils.CommonUtils;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.Date;

/**
 * String to Date Converter
 * e.g. "2022-03-26T11:49:00+00:00"
 */
public class DateISOParser extends StdDeserializer<Date>  {

    public DateISOParser() {
        this(null);
    }

    public DateISOParser(Class<?> vc) {
        super(vc);
    }

    @Override
    public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        return CommonUtils.str2ISO(jsonParser.getText());
    }
}
