package com.chrischou.service;

import com.chrischou.common.model.CoinDeskOriData;
import com.chrischou.controller.model.CurrencyDTO;
import com.chrischou.controller.model.CustomCoinDeskDTO;
import com.chrischou.controller.request.CurrencyAddRequest;
import com.chrischou.controller.request.CurrencyUpdateRequest;

public interface CurrencyService {

    CustomCoinDeskDTO getCoinDeskData();

    CurrencyDTO findById(long id);

    CurrencyDTO updateCurrency(long id, CurrencyUpdateRequest request);

    CurrencyDTO addCurrency(CurrencyAddRequest request);

    void deleteCurrency(long id);
}
