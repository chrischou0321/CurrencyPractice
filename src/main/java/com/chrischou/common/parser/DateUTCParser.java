package com.chrischou.common.parser;

import com.chrischou.common.utils.CommonUtils;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.Date;

/**
 * String to Date Converter
 * e.g. "Mar 26, 2022 11:49:00 UTC"
 */
public class DateUTCParser extends StdDeserializer<Date> {

    public DateUTCParser() {
        this(null);
    }

    protected DateUTCParser(JavaType valueType) {
        super(valueType);
    }

    @Override
    public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {
        return CommonUtils.str2UTC(jsonParser.getText());
    }
}
