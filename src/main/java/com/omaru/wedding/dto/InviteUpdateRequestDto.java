package com.omaru.wedding.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record InviteUpdateRequestDto(
        @NotNull Short attendance,
        @Size(max = 200) String companionsText,
        @Size(max = 100) String name,
        List<String> allergiesList
) {
}
