package com.chrischou.controller;

import com.chrischou.CommonTestUtils;
import com.chrischou.common.model.GlobalResponse;
import com.chrischou.controller.model.CurrencyDTO;
import com.chrischou.controller.model.CustomCoinDeskDTO;
import com.chrischou.controller.model.CustomCoinDeskData;
import com.chrischou.controller.request.CurrencyAddRequest;
import com.chrischou.controller.request.CurrencyUpdateRequest;
import com.chrischou.enums.RespStatus;
import com.chrischou.exceptions.CurrencyNotFoundException;
import com.chrischou.repository.CurrencyRepository;
import com.chrischou.repository.model.CurrencyPO;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CurrencyControllerTestCase {

    private final String URI = "/currency";
    private final String URI_WITH_ID = URI + "/{id}";

    @Autowired
    private MockMvc mock;
    @Autowired
    private CurrencyRepository currencyRepository;

    @Test
    public void testCustomCoinDesk() throws Exception {
        MockHttpServletResponse resp = mock.perform(
                MockMvcRequestBuilders
                        .get(URI + "/coindesk")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        // response status
        Assert.assertEquals(200, resp.getStatus());
        System.out.println(resp.getContentAsString());
        GlobalResponse<CustomCoinDeskDTO> controllerResp = CommonTestUtils.generateResponse(resp.getContentAsString(), CustomCoinDeskDTO.class);
        Assert.assertNotNull(controllerResp);
        // operation code
        Assert.assertEquals(RespStatus.SUCCESS.getCode(), controllerResp.getCode());
        // data
        CustomCoinDeskDTO result = controllerResp.getData();
        Assert.assertNotNull(result);
        // regex, e.g. "1990/01/23 01:54:32"
        Assert.assertTrue(Pattern.matches("^\\d{4}\\/\\d{2}\\/\\d{2}\\s\\d{2}:\\d{2}:\\d{2}$", result.getLastUpdateTime()));
        List<CustomCoinDeskData> currencyList = result.getCurrencyList();
        Assert.assertTrue(currencyList != null && currencyList.size() > 0);
        // Upper Case words
        Pattern patternEN = Pattern.compile("^[A-Z]+$");
        currencyList.forEach(c -> {
            Assert.assertTrue(patternEN.matcher(c.getName()).find());
            Assert.assertTrue(StringUtils.isNotBlank(c.getNameTW()));
            BigDecimal rate = c.getExchangeRate();
            Assert.assertTrue(rate != null && rate.compareTo(BigDecimal.ZERO) > 0);
        });
    }

    @Test
    public void testAddCurrency() throws Exception {
        // base request
        CurrencyAddRequest request = new CurrencyAddRequest("ZCA", "$^", new BigDecimal("25.4321"), null);
        long id = testSimpleAddCurrency(request);
        testAddDuplicateCurrency(request);
        currencyRepository.deleteById(id);
    }

    @Test
    public void testUpdateCurrency() throws Exception {
        testNormalUpdateCurrency();
        testUpdateCurrencyWithDuplicateCode();
    }

    private void testNormalUpdateCurrency() throws Exception {
        long id = 2;
        CurrencyUpdateRequest request = new CurrencyUpdateRequest("USB", "^^", new BigDecimal("1.2345"), "update for 02");
        MockHttpServletResponse resp = mock.perform(
                MockMvcRequestBuilders
                        .put(URI_WITH_ID, id)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(CommonTestUtils.toJson(request)))
                .andReturn().getResponse();
        // response status
        Assert.assertEquals(200, resp.getStatus());
        System.out.println(resp.getContentAsString());
        GlobalResponse<CurrencyDTO> controllerResp = CommonTestUtils.generateResponse(resp.getContentAsString(), CurrencyDTO.class);
        Assert.assertNotNull(controllerResp);
        // operation code
        Assert.assertEquals(RespStatus.SUCCESS.getCode(), controllerResp.getCode());
        // data
        CurrencyDTO result = controllerResp.getData();
        Assert.assertNotNull(result);
        Assert.assertEquals(id, result.getId());
        Assert.assertEquals(request.getCode(), result.getCode());
        Assert.assertEquals(request.getSymbol(), result.getSymbol());
        Assert.assertEquals(request.getRate(), result.getRate());
        if (StringUtils.isNotBlank(request.getDesc())) {
            Assert.assertEquals(request.getDesc(), result.getDesc());
        }
        System.out.println(result.toString());
    }

    private void testUpdateCurrencyWithDuplicateCode() throws Exception {
        long id = 2;
        CurrencyUpdateRequest request = new CurrencyUpdateRequest("USD", "^^", new BigDecimal("1.2345"), "update for 02");
        MockHttpServletResponse resp = mock.perform(
                MockMvcRequestBuilders
                        .put(URI_WITH_ID, id)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(CommonTestUtils.toJson(request)))
                .andReturn().getResponse();
        // response status
        Assert.assertEquals(200, resp.getStatus());
        System.out.println(resp.getContentAsString());
        GlobalResponse<CurrencyDTO> controllerResp = CommonTestUtils.generateResponse(resp.getContentAsString(), CurrencyDTO.class);
        Assert.assertNotNull(controllerResp);
        // operation code
        Assert.assertEquals(RespStatus.ERROR.getCode(), controllerResp.getCode());
        // data
        Assert.assertNull(controllerResp.getData());
    }

    @Test
    public void testDeleteCurrency() throws Exception {
        // insert custom currency
        CurrencyPO sample = new CurrencyPO()
                .setCode("AAA").setSymbol("@v@").setRate(new BigDecimal("0.321"))
                .setCreateTime(new Date()).setUpdateTime(new Date());
        currencyRepository.saveAndFlush(sample);
        long id = sample.getId();
        // save sample currency success
        Assert.assertTrue(id > 0);
        MockHttpServletResponse resp = mock.perform(
                MockMvcRequestBuilders
                        .delete(URI_WITH_ID, id)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        // response status
        Assert.assertEquals(200, resp.getStatus());
        System.out.println(resp.getContentAsString());
        GlobalResponse<Boolean> controllerResp = CommonTestUtils.generateResponse(resp.getContentAsString(), Boolean.class);
        Assert.assertNotNull(controllerResp);
        // operation code
        Assert.assertEquals(RespStatus.SUCCESS.getCode(), controllerResp.getCode());
        // data
        Assert.assertTrue(controllerResp.getData());
    }

    @Test
    public void testLoadCurrency() throws Exception {
        testLoadExistCurrency();
        testLoadNotExistCurrency();
    }

    private void testLoadExistCurrency() throws Exception {
        long id = 1;
        // get source data to compare
        CurrencyPO source = currencyRepository.findById(id).orElseThrow(CurrencyNotFoundException::new);
        // start call Load API
        MockHttpServletResponse resp = mock.perform(
                MockMvcRequestBuilders
                        .get(URI_WITH_ID, id)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        // response status
        Assert.assertEquals(200, resp.getStatus());
        System.out.println(resp.getContentAsString());
        GlobalResponse<CurrencyDTO> controllerResp = CommonTestUtils.generateResponse(resp.getContentAsString(), CurrencyDTO.class);
        Assert.assertNotNull(controllerResp);
        // operation code
        Assert.assertEquals(RespStatus.SUCCESS.getCode(), controllerResp.getCode());
        // data
        CurrencyDTO result = controllerResp.getData();
        Assert.assertNotNull(result);
        Assert.assertEquals(source.getId(), result.getId());
        Assert.assertEquals(source.getCode(), result.getCode());
        Assert.assertEquals(source.getSymbol(), result.getSymbol());
        Assert.assertEquals(source.getRate(), result.getRate());
        Assert.assertEquals(source.getDesc(), result.getDesc());
        System.out.println(result.toString());
    }

    private void testLoadNotExistCurrency() throws Exception {
        long id = 99999;
        MockHttpServletResponse resp = mock.perform(
                MockMvcRequestBuilders
                        .get(URI_WITH_ID, id)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        // response status
        Assert.assertEquals(200, resp.getStatus());
        System.out.println(resp.getContentAsString());
        GlobalResponse<CurrencyDTO> controllerResp = CommonTestUtils.generateResponse(resp.getContentAsString(), CurrencyDTO.class);
        Assert.assertNotNull(controllerResp);
        // operation code
        Assert.assertEquals(RespStatus.ERROR.getCode(), controllerResp.getCode());
        // data
        Assert.assertNull(controllerResp.getData());
    }

    private long testSimpleAddCurrency(CurrencyAddRequest request) throws Exception {
        MockHttpServletResponse resp = mock.perform(
                MockMvcRequestBuilders
                        .post(URI)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(CommonTestUtils.toJson(request)))
                .andReturn().getResponse();
        // response status
        Assert.assertEquals(200, resp.getStatus());
        System.out.println(resp.getContentAsString());
        GlobalResponse<CurrencyDTO> controllerResp = CommonTestUtils.generateResponse(resp.getContentAsString(), CurrencyDTO.class);
        Assert.assertNotNull(controllerResp);
        // operation code
        Assert.assertEquals(RespStatus.SUCCESS.getCode(), controllerResp.getCode());
        // data
        CurrencyDTO result = controllerResp.getData();
        Assert.assertNotNull(result);
        Assert.assertTrue(result.getId() > 0);
        Assert.assertEquals(request.getCode(), result.getCode());
        Assert.assertEquals(request.getSymbol(), result.getSymbol());
        Assert.assertEquals(request.getRate(), result.getRate());
        if (StringUtils.isNotBlank(request.getDesc())) {
            Assert.assertEquals(request.getDesc(), result.getDesc());
        }
        return result.getId();
    }

    private void testAddDuplicateCurrency(CurrencyAddRequest request) throws Exception {
        MockHttpServletResponse resp = mock.perform(
                MockMvcRequestBuilders
                        .post(URI)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(CommonTestUtils.toJson(request)))
                .andReturn().getResponse();
        // response status
        Assert.assertEquals(200, resp.getStatus());
        System.out.println(resp.getContentAsString());
        GlobalResponse<CurrencyDTO> controllerResp = CommonTestUtils.generateResponse(resp.getContentAsString(), CurrencyDTO.class);
        Assert.assertNotNull(controllerResp);
        // operation code
        Assert.assertEquals(RespStatus.ERROR.getCode(), controllerResp.getCode());
        // data
        Assert.assertNull(controllerResp.getData());
    }
}
