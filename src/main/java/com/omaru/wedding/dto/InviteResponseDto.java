package com.omaru.wedding.dto;

import java.util.List;

//DBのidや内部フラグ、監査系（createdAt等）は基本返さない
public record InviteResponseDto(
        String inviteToken,
        short attendance,
        String name,
        String companionsText,
        List<String> allergiesList
) {
}
