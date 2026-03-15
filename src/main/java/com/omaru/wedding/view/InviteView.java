package com.omaru.wedding.view;

import com.omaru.wedding.dto.InviteResponseDto;
import com.omaru.wedding.entity.InviteEntity;

import java.util.Arrays;
import java.util.List;

public class InviteView {
    public static InviteResponseDto toDto(InviteEntity entity) {
        List<String> allergiesList =
                entity.getAllergiesText() == null ? List.of() : Arrays.asList(entity.getAllergiesText().split(","));


        return new InviteResponseDto(
                entity.getInviteToken(),
                entity.getAttendance(),
                entity.getName(),
                entity.getCompanionsText(),
                allergiesList
        );
    }
}
