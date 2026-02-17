package com.banking.service.inter;

import com.banking.dto.request.MoneyTransferRequest;
import com.banking.dto.event.Event;
import com.banking.dto.request.CustomerSaveRequest;
import com.banking.dto.request.PurchaseRequest;
import com.banking.dto.request.TopUpRequest;
import com.banking.entity.Transaction;

import java.math.BigDecimal;

public interface CustomerServiceInter {


    void save(CustomerSaveRequest request);


    BigDecimal getBalance(Long id);
}
