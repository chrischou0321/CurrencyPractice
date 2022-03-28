package com.chrischou.common.parser;

import com.chrischou.common.utils.CommonUtils;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.Date;

/**
 * String to Date converter
 * e.g. "Mar 26, 2022 at 11:49 GMT"
 */
public class DateGMTParser extends StdDeserializer<Date>  {

    public DateGMTParser() {
        this(null);
    }

    public DateGMTParser(JavaType valueType) {
        super(valueType);
    }

    @Override
    public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        return CommonUtils.str2DateByZone(jsonParser.getText());
    }
}
