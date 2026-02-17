package com.banking.service.inter;

import com.banking.dto.request.CustomerSaveRequest;

import java.math.BigDecimal;

public interface CustomerServiceInter {


    void save(CustomerSaveRequest request);


    BigDecimal getBalance(Long id);
}
