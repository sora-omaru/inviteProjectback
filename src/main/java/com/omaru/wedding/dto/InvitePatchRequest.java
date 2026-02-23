package com.omaru.wedding.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record InvitePatchRequest (
    @Min(0) @Max(2) Short attendance,
    String message
){}
