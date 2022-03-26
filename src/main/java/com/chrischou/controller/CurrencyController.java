package com.chrischou.controller;

import com.chrischou.common.model.GlobalResponse;
import com.chrischou.common.utils.CommonUtils;
import com.chrischou.controller.model.CurrencyDTO;
import com.chrischou.controller.model.CustomCoinDeskDTO;
import com.chrischou.controller.request.CurrencyAddRequest;
import com.chrischou.controller.request.CurrencyUpdateRequest;
import com.chrischou.enums.RespStatus;
import com.chrischou.exceptions.OperationFailedException;
import com.chrischou.service.CurrencyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/currency")
public class CurrencyController {

    private static final Logger log = LoggerFactory.getLogger(CurrencyController.class);

    @Autowired
    private CurrencyService currencyService;

    @GetMapping("/coindesk")
    public GlobalResponse<CustomCoinDeskDTO> getCoinDeskData() {
        return new GlobalResponse<>(currencyService.getCoinDeskData());
    }

    @GetMapping(value = "/{id}")
    public GlobalResponse<CurrencyDTO> loadOne(@PathVariable long id) {
        log.info("load currency: " + id);
        try {
            return new GlobalResponse<>(currencyService.findById(id));
        }
        catch (OperationFailedException e) {
            return new GlobalResponse<>(RespStatus.ERROR, e.getMessage());
        }
        catch (Exception e) {
            return new GlobalResponse<>(RespStatus.UNKNOWN, e.getMessage());
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public GlobalResponse<CurrencyDTO> addCurrency(@Valid @RequestBody CurrencyAddRequest request,
                                                   @Validated Errors errors) {
        log.info("addCurrency: " + request.toString());
        if (errors.hasErrors()) {
            return new GlobalResponse<>(RespStatus.INVALID, CommonUtils.collectRequestInvalidErrorsMessage(errors));
        }
        try {
            return new GlobalResponse<>(currencyService.addCurrency(request));
        }
        catch (OperationFailedException e) {
            return new GlobalResponse<>(RespStatus.ERROR, e.getMessage());
        }
        catch (Exception e) {
            return new GlobalResponse<>(RespStatus.UNKNOWN, e.getMessage());
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public GlobalResponse<CurrencyDTO> updateCurrency(@PathVariable long id,
                                                      @Valid @RequestBody CurrencyUpdateRequest request,
                                                      @Validated Errors errors) {
        log.info("updateCurrency: " + request.toString());
        if (errors.hasErrors()) {
            errors.getAllErrors().forEach(e -> log.info(e.getDefaultMessage()));
            return new GlobalResponse<>(RespStatus.INVALID, CommonUtils.collectRequestInvalidErrorsMessage(errors));
        }
        try {
            return new GlobalResponse<>(currencyService.updateCurrency(id, request));
        }
        catch (OperationFailedException e) {
            return new GlobalResponse<>(RespStatus.ERROR, e.getMessage());
        }
        catch (Exception e) {
            return new GlobalResponse<>(RespStatus.UNKNOWN, e.getMessage());
        }
    }

    @DeleteMapping(value = "/{id}")
    public GlobalResponse<Boolean> deleteCurrency(@PathVariable long id) {
        log.info("delete currency: " + id);
        try {
            currencyService.deleteCurrency(id);
            return new GlobalResponse<>(true);
        }
        catch (OperationFailedException e) {
            return new GlobalResponse<>(RespStatus.ERROR, e.getMessage(), false);
        }
        catch (Exception e) {
            return new GlobalResponse<>(RespStatus.UNKNOWN, e.getMessage(), false);
        }
    }

//    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
//    public GlobalResponse<List<CurrencyDTO>> getCurrencyList() {
//
//    }
}
