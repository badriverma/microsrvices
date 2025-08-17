package com.microservices.service.impl;

import com.microservices.dto.*;
import com.microservices.entity.Accounts;
import com.microservices.entity.Customer;
import com.microservices.exception.ResourceNotFoundException;
import com.microservices.mapper.AccountsMapper;
import com.microservices.mapper.CustomerMapper;
import com.microservices.repository.AccountsRepository;
import com.microservices.repository.CustomerRepository;
import com.microservices.service.CustomerService;
import com.microservices.service.client.CardsClient;
import com.microservices.service.client.LoansClient;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
//@AllArgsConstructor
//@NoArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AccountsRepository accountsRepository;

    @Autowired
    private CardsClient cardsClient;

    @Autowired
    private LoansClient loansClient;

    @Override
    public CustomerDetailsDto fetchCustomerDetails(String mobileNumber) {

        Customer customer = this.customerRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "Mobile Number", mobileNumber));

        Accounts accounts = this.accountsRepository.findByCustomerId(customer.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Accounts", "Customer Id", customer.getCustomerId().toString()));

        CustomerDetailsDto customerDetailsDto = CustomerMapper.mapToCustomerDetailsDto(customer, new CustomerDetailsDto());
        customerDetailsDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));

        ResponseEntity<LoansDto> loansDtoResponseEntity = loansClient.fetchLoanDetails(mobileNumber);
        customerDetailsDto.setLoansDto(loansDtoResponseEntity.getBody());

        ResponseEntity<CardsDto> cardsDtoResponseEntity = cardsClient.fetchCardDetails(mobileNumber);
        customerDetailsDto.setCardsDto(cardsDtoResponseEntity.getBody());

        return customerDetailsDto;
    }
}
