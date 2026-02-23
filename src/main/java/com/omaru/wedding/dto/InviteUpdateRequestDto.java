package com.omaru.wedding.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record InviteUpdateRequestDto(
        @NotNull Short attendance,
        @Size(max = 200) String companionsText
) {
}
