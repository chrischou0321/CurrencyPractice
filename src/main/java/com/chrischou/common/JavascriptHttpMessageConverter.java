package com.chrischou.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class JavascriptHttpMessageConverter extends AbstractHttpMessageConverter {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new JavascriptHttpMessageConverter());
        return restTemplate;
    }

    public JavascriptHttpMessageConverter() {
        // content-type: application/javascript
        super(new MediaType("application", "javascript", StandardCharsets.UTF_8));
    }

    @Override
    protected boolean supports(Class clazz) {
        return true;
    }

    @Override
    protected Object readInternal(Class clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        InputStream body = inputMessage.getBody();
        return objectMapper.readValue(body, clazz);
    }

    @Override
    protected void writeInternal(Object o, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        // nothing to do
    }
}
