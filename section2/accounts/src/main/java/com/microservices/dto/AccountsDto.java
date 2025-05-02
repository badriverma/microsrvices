package com.microservices.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Schema(
        name = "Account",
        description = "Schema hold to Account information"
)
public class AccountsDto {

    @Pattern(regexp = "(^$|[0-9]{10})",message = "Account number should be 10 numbers")
    @Schema(
            description = "Account Number of EazyBank account",
            example = "9876543210"
    )
    private Long accountNumber;

    @NotEmpty(message = "Account types can not be a null or empty")
    @Schema(
            description = "Account Type of EazyBank account",
            example = "Savings"
    )
    private String accountType;

    @NotEmpty(message = "Branch address can not be a null or empty")
    @Schema(
            description = "Branch Address of EazyBank account",
            example = "Mamura Sector 66, Noida UP 201301"
    )
    private String branchAddress;
}
