package com.chrischou.template;

import com.chrischou.common.model.CoinDeskOriData;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CoinDeskTemplateTestCase {

    @Autowired
    private CoinDeskTemplate coinDeskTemplate;

    @Test
    public void testCallCoinDesk() {
        CoinDeskOriData data = coinDeskTemplate.getCoinDeskOriData();
        System.out.println(data.toString());
        // time
        Assert.assertNotNull(data.getTime());
        Assert.assertNotNull(data.getTime().getUpdated());
        Assert.assertNotNull(data.getTime().getUpdatedISO());
        Assert.assertNotNull(data.getTime().getUpdateduk());
        // disclaimer
        Assert.assertTrue(StringUtils.isNotBlank(data.getDisclaimer()));
        // chartName
        Assert.assertTrue(StringUtils.isNotBlank(data.getChartName()));
        // bpi
        Assert.assertNotNull(data.getBpi());
        Assert.assertEquals(3, data.getBpi().size());
        // currency
        data.getBpi().forEach((name, currencyData) -> {
            Assert.assertTrue(StringUtils.isNoneBlank(name));
            Assert.assertNotNull(currencyData);
            Assert.assertTrue(StringUtils.isNoneBlank(currencyData.getCode()));
            Assert.assertTrue(StringUtils.isNoneBlank(currencyData.getSymbol()));
            Assert.assertTrue(StringUtils.isNoneBlank(currencyData.getRate()));
            Assert.assertTrue(StringUtils.isNoneBlank(currencyData.getDesc()));
            Assert.assertTrue(currencyData.getRate() != null
                    && currencyData.getRateFloat().compareTo(BigDecimal.ZERO) > 0);
        });
    }
}
