package com.chrischou.service.impl;

import com.chrischou.common.model.CoinDeskCurrencyData;
import com.chrischou.common.model.CoinDeskOriData;
import com.chrischou.common.utils.CommonUtils;
import com.chrischou.controller.model.CurrencyDTO;
import com.chrischou.controller.model.CustomCoinDeskDTO;
import com.chrischou.controller.model.CustomCoinDeskData;
import com.chrischou.controller.request.CurrencyAddRequest;
import com.chrischou.controller.request.CurrencyUpdateRequest;
import com.chrischou.enums.Currency;
import com.chrischou.exceptions.CurrencyNotFoundException;
import com.chrischou.exceptions.DuplicateCurrencyException;
import com.chrischou.exceptions.OperationFailedException;
import com.chrischou.repository.CurrencyRepository;
import com.chrischou.repository.model.CurrencyPO;
import com.chrischou.service.CurrencyService;
import com.chrischou.template.CoinDeskTemplate;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.*;

@Service
@Transactional
public class CurrencyServiceImpl implements CurrencyService {

    private static final Logger log = LoggerFactory.getLogger(CurrencyServiceImpl.class);

    @Autowired
    private CoinDeskTemplate coinDeskTemplate;

    @Autowired
    private CurrencyRepository currencyRepository;

    @Override
    public CustomCoinDeskDTO getCoinDeskData() {
        CoinDeskOriData responseData = coinDeskTemplate.getCoinDeskOriData();
        if (Objects.isNull(responseData)) {
            throw new OperationFailedException("Fetch coindesk api failed");
        }
        CustomCoinDeskDTO dto = new CustomCoinDeskDTO();
        dto.setLastUpdateTime(CommonUtils.date2StrForDTO(responseData.getLastUpdateTime()));
        dto.setCurrencyList(generateCurrencyList(responseData.getBpi()));
        return dto;
    }

    private List<CustomCoinDeskData> generateCurrencyList(Map<String, CoinDeskCurrencyData> bpi) {
        List<CustomCoinDeskData> result = new ArrayList<>();
        if (Objects.isNull(bpi)) {
            return result;
        }
        bpi.forEach((code, oriData) -> {
            Currency currency = Currency.get(code);
            CustomCoinDeskData data = new CustomCoinDeskData()
                    .setName(currency.name())
                    .setNameTW(currency.getCodeTW())
                    .setExchangeRate(oriData.getRateFloat());
            result.add(data);
        });
        return result;
    }

    @Override
    public CurrencyDTO findById(long id) {
        CurrencyPO po = currencyRepository.findById(id)
                .orElseThrow(CurrencyNotFoundException::new);
        return CurrencyDTO.fromPO(po);
    }

    @Override
    public CurrencyDTO updateCurrency(long id, CurrencyUpdateRequest request) {
        if (id <= 0) {
            log.error("invalid id: {}", id);
            throw new OperationFailedException("Invalid identity");
        }
        if (isCurrencyInvalid(request.getCode(), request.getRate())) {
            log.error("update invalid currency, code: {}, rate: {}", request.getCode(), request.getRate());
            throw new OperationFailedException("This request for updating is invalid.");
        }
        Optional<CurrencyPO> sameCodeCurrency = currencyRepository.findByCode(request.getCode());
        // avoid duplicate code
        if (sameCodeCurrency.isPresent() && sameCodeCurrency.get().getId() != id) {
            throw new DuplicateCurrencyException();
        }
        CurrencyPO existCurrency = sameCodeCurrency.orElseGet(() -> currencyRepository.findById(id).orElseThrow(CurrencyNotFoundException::new));
        existCurrency.setCode(request.getCode());
        existCurrency.setSymbol(request.getSymbol());
        existCurrency.setRate(request.getRate());
        existCurrency.setDesc(request.getDesc());
        existCurrency.setUpdateTime(new Date());
        return CurrencyDTO.fromPO(existCurrency);
    }

    private boolean isCurrencyInvalid(String code, BigDecimal rate) {
        if (StringUtils.isBlank(code) || Objects.isNull(rate)) {
            return true;
        }
        if (code.length() > 3) {
            return true;
        }
        if (rate.compareTo(BigDecimal.ZERO) <= 0) {
            return true;
        }
        return false;
    }

    @Override
    public CurrencyDTO addCurrency(CurrencyAddRequest request) {
        if (isCurrencyInvalid(request.getCode(), request.getRate())) {
            log.error("add invalid currency, code: {}, rate: {}", request.getCode(), request.getRate());
            throw new OperationFailedException("This request for adding is invalid.");
        }
        Optional<CurrencyPO> existCurrency = currencyRepository.findByCode(request.getCode());
        if (existCurrency.isPresent()) {
            throw new DuplicateCurrencyException();
        }
        CurrencyPO po = new CurrencyPO();
        po.setCode(request.getCode());
        po.setSymbol(request.getSymbol().toLowerCase());
        po.setRate(request.getRate());
        po.setDesc(request.getDesc());
        Date curr = new Date();
        po.setCreateTime(curr);
        po.setUpdateTime(curr);
        currencyRepository.saveAndFlush(po);
        return CurrencyDTO.fromPO(po);
    }

    @Override
    public void deleteCurrency(long id) {
        Optional<CurrencyPO> existCurrency = currencyRepository.findById(id);
        if (!existCurrency.isPresent()) {
            log.warn("currency id[{}] has already been deleted.", id);
            return;
        }
        currencyRepository.deleteById(id);
    }
}
