package com.microservices.controller;

import com.microservices.constant.AccountsConstant;
import com.microservices.dto.AccountsContactInfoDto;
import com.microservices.dto.CustomerDto;
import com.microservices.dto.ErrorResponseDto;
import com.microservices.dto.ResponseDto;
import com.microservices.service.AccountsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/accounts", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
@Tag(
        name = "CRUD REST API for Accounts in EazyBank",
        description = "CRUD REST API in EazyBank to CREATE, FETCH, UPDATE and DELETE Account Details"
)
public class AccountsController {

    private AccountsService accountsService;

    public AccountsController(AccountsService accountsService){
        this.accountsService = accountsService;
    }

    @Autowired
    private Environment environment;

    @Autowired
    private AccountsContactInfoDto accountsContactInfoDto;

    @Value("${build.version}")
    private String buildVersion;

    @PostMapping("/create")
    @Operation(
            summary = "Create Account REST API",
            description = "REST API to create new Customer & Account inside EazyBank"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP Status CREATED"
    )
    public ResponseEntity<ResponseDto> createAccounts(@Valid @RequestBody CustomerDto customerDto){
        accountsService.createAccounts(customerDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto(AccountsConstant.STATUS_201,AccountsConstant.MESSAGE_201));
    }

    @GetMapping("/fetch")
    @Operation(
            summary = "Fetch Customer & Account Details REST API",
            description = "REST API to Fetch Customer & Account Details from EazyBank"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status OK"
    )
    public ResponseEntity<CustomerDto> fetchAccountsDetails(@RequestParam
                                                                @Pattern(regexp = "(^$|[0-9]{10})",message = "Account number should be 10 numbers")
                                                                String mobileNumber){
        CustomerDto customerDto = accountsService.fetchAccountDetails(mobileNumber);
        return new ResponseEntity<>(customerDto,HttpStatus.OK);
    }

    @PutMapping("/update")
    @Operation(
            summary = "Update Customer & Account Details REST API",
            description = "REST API to Update Customer & Account Details"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    public ResponseEntity<ResponseDto> updateAccountDetails(@Valid @RequestBody CustomerDto customerDto){
        ResponseEntity<ResponseDto> result;
        boolean isUpdated = accountsService.updateAccountDetails(customerDto);
        if(isUpdated){
            result = ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDto(AccountsConstant.STATUS_200, AccountsConstant.MESSAGE_200));
        }else {
            result = ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(AccountsConstant.STATUS_417, AccountsConstant.MESSAGE_417_UPDATE));
        }
        return result;
    }

    @DeleteMapping("/delete")
    @Operation(
            summary = "Delete Customer & Account Details REST API",
            description = "REST API TO Delete Customer & Account Details"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    public  ResponseEntity<ResponseDto> deleteAccountDetails(@RequestParam
                                                                 @Pattern(regexp = "(^$|[0-9]{10})",message = "Account number should be 10 numbers")
                                                                 String mobileNumber){
        boolean isDeleted = accountsService.deleteAccountDetails(mobileNumber);
        if(isDeleted){
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseDto(AccountsConstant.STATUS_200,AccountsConstant.MESSAGE_200));
        }else {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(AccountsConstant.STATUS_417,AccountsConstant.UPDATE_417_DELETE));
        }
    }

    @Operation(
            summary = "Build Version Information",
            description = "REST API to Fetch Build Version Information"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @GetMapping("/build-info")
    public ResponseEntity<String> getBuildVersion(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(buildVersion);
    }

    @Operation(
            summary = "Get Maven Path",
            description = "Get Maven Path that is installed in my system"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Http status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Http status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @GetMapping("/java-version")
    public ResponseEntity<String> getJavaVersion(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(environment.getProperty("MAVEN_HOME"));

    }

    @Operation(
            summary = "Get Contact information",
            description = "Get Contact information"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Http status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Http status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @GetMapping("/contact-info")
    public ResponseEntity<AccountsContactInfoDto> getContactInfo(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(accountsContactInfoDto);

    }

}
