package com.microservices.service.client;

import com.microservices.dto.CardsDto;
import com.microservices.dto.LoansDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("loans")
public interface LoansClient {

    @GetMapping(value = "/api/fetch", consumes = "application/json")
    public ResponseEntity<LoansDto> fetchLoanDetails(@RequestParam String mobileNumber);


}
