package com.omaru.wedding.view;

import com.omaru.wedding.dto.InviteResponseDto;
import com.omaru.wedding.entity.InviteEntity;

public class InviteView {
    public static InviteResponseDto toDto(InviteEntity entity) {
        return new InviteResponseDto(
                entity.getInviteToken(),
                entity.getAttendance(),
                entity.getName(),
                entity.getCompanionsText()
        );
    }
}
