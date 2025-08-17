package com.microservices.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(
        name = "CustomerDetails",
        description = "Schema to hold Customer, Account, Cards and Loans information"
)
public class CustomerDetailsDto {

    @NotEmpty(message = "Name can not be a null or empty")
    @Size(min = 3, max = 30, message = "The length of the customer name should between 3 to 30")
    @Schema(
            description = "Name of the customer",
            example = "Badri Prasad Verma"
    )
    private String name;

    @NotEmpty(message = "Email can not be a null or empty")
    @Email(message = "Email should be a valid input value")
    @Schema(
            description = "Email Address of the customer",
            example = "badriverma135@gmail.com"
    )
    private String email;

    @Pattern(regexp = "(^$|[0-9]{10})",message = "Mobile number should be 10 numbers")
    @Schema(
            description = "Mobile Number of the customer",
            example = "9372458650"
    )
    private String mobileNumber;

    @Schema(
            description = "Account Details of the customer"
    )
    private AccountsDto accountsDto;

    @Schema(
            description = "Cards Details of the customer"
    )
    private CardsDto cardsDto;

    @Schema(
            description = "Loans Details of the customer"
    )
    private LoansDto loansDto;
}
