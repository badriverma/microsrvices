package com.microservices.service;

import com.microservices.dto.CustomerDto;

public interface AccountsService {

    void createAccounts(CustomerDto customerDto);

    CustomerDto fetchAccountDetails(String mobileNumber);

    boolean updateAccountDetails(CustomerDto customerDto);

    boolean deleteAccountDetails(String mobileNumber);
}
