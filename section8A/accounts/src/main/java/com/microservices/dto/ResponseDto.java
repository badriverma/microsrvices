package com.microservices.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(
        name = "Response",
        description = "Schema hold to successful response information"
)
public class ResponseDto {

    @Schema(
            description = "Status code in the response",
            example = "200"
    )
    private String statusCode;

    @Schema(
            description = "Status message of the response",
            example = "Request Processed Successfully"
    )
    private String statusMsg;
}
