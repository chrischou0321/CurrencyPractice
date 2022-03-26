package com.chrischou.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CurrencyRepositoryTestCase {

    @Autowired
    private CurrencyRepository currencyRepository;

    @Test
    public void testCallCurrencyControllerLoad() {
        System.out.println("testCallCurrencyControllerLoad");
    }

    @Test
    public void testCallCurrencyControllerAdd() {
        System.out.println("testCallCurrencyControllerAdd");
    }

    @Test
    public void testCallCurrencyControllerUpdate() {
        System.out.println("testCallCurrencyControllerUpdate");
    }

    @Test
    public void testCallCurrencyControllerDelete() {
        System.out.println("testCallCurrencyControllerDelete");
    }

    @Test
    public void testCallCoinDeskAPI() {
        System.out.println("testCallCoinDeskAPI");
    }

    @Test
    public void testCallCustomCoinDeskAPI() {
        System.out.println("testCallCustomCoinDeskAPI");
    }
}
