package com.omaru.wedding.dto;

import java.time.OffsetDateTime;

public record AdminInviteListItemDto(
        String inviteToken,
        String name,
        short attendance,
        String companionsText,
//            String allergiesText,
        String kidsText,
//            String messageText,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {

}
