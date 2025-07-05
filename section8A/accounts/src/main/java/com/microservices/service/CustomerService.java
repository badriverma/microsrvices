package com.microservices.service;

import com.microservices.dto.CustomerDetailsDto;

public interface CustomerService {

    CustomerDetailsDto fetchCustomerDetails(String mobileNumber);
}
