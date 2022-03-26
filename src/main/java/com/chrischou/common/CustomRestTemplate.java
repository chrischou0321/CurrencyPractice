package com.chrischou.common;

import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class CustomRestTemplate extends RestTemplate {

    public CustomRestTemplate() {
        super();
        getMessageConverters().add(new JavascriptHttpMessageConverter());
    }

    public CustomRestTemplate(ClientHttpRequestFactory requestFactory) {
        super(requestFactory);
        getMessageConverters().add(new JavascriptHttpMessageConverter());
    }

    public CustomRestTemplate(List<HttpMessageConverter<?>> messageConverters) {
        super(messageConverters);
        getMessageConverters().add(new JavascriptHttpMessageConverter());
    }
}
