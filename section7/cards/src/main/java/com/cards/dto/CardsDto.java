package com.cards.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Schema(
        name = "Cards",
        description = "Schema to hold Card information"
)
public class CardsDto {

    @NotEmpty(message = "Mobile number can not be a null or empty")
    @Pattern(regexp = "(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
    @Schema(
            description = "Mobile number of Customer",
            example = "9876543210"
    )
    private String mobileNumber;

    @NotEmpty(message = "Card number can not be a null or empty")
    @Pattern(regexp = "(^$|[0-9]{12})",message = "Mobile number must be 12 digits")
    @Schema(
            description = "Card number of Customer",
            example = "100000000001"
    )
    private String cardNumber;

    @NotEmpty(message = "CardType can not be a null or empty")
    @Schema(
            description = "Type of the card", example = "Credit Card"
    )
    private String cardType;

    @Positive(message = "Total card limit should be greater than zero")
    @Schema(
            description = "Total amount limit available against a card", example = "100000"
    )
    private int totalLimit;


    @PositiveOrZero(message = "Total amount used should be equal or greater than zero")
    @Schema(
            description = "Total amount used by a Customer", example = "1000"
    )
    private int amountUsed;

    @PositiveOrZero(message = "Total available amount should be equal or greater than zero")
    @Schema(
            description = "Total available amount against a card", example = "90000"
    )
    private int availableAmount;
}
