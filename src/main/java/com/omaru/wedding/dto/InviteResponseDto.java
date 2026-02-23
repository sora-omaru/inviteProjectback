package com.omaru.wedding.dto;
//DBのidや内部フラグ、監査系（createdAt等）は基本返さない
public record InviteResponseDto(
        String inviteToken,
        short attendance,
        String name,
        String companionsText
) {
}
