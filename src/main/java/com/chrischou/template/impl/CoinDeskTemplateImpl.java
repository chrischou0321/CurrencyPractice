package com.chrischou.template.impl;

import com.chrischou.common.CustomRestTemplate;
import com.chrischou.common.model.CoinDeskOriData;
import com.chrischou.template.CoinDeskTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;

@Component
public class CoinDeskTemplateImpl extends CustomRestTemplate implements CoinDeskTemplate {

    private static final Logger log = LoggerFactory.getLogger(CoinDeskTemplateImpl.class);

    private final String URL_COIN_DESK = "https://api.coindesk.com/v1/bpi/currentprice.json";

    @Override
    public CoinDeskOriData getCoinDeskOriData() {
        try {
            ResponseEntity<CoinDeskOriData> response = getForEntity(new URI(URL_COIN_DESK), CoinDeskOriData.class);
            return response.getBody();
        } catch (URISyntaxException e) {
            log.error("Request from coindesk failed: ", e);
        }
        return null;
    }
}
