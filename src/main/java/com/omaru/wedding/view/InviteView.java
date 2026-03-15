package com.omaru.wedding.view;

import com.omaru.wedding.dto.InviteResponseDto;
import com.omaru.wedding.entity.InviteEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InviteView {
    public static InviteResponseDto toDto(InviteEntity entity) {
        List<String> allergiesList =
                entity.getAllergiesText() == null || entity.getAllergiesText().isBlank() ? List.of() : Arrays.stream(entity.getAllergiesText().split(",")).map(String::trim).filter(s -> !s.isEmpty()).toList();

        return new InviteResponseDto(
                entity.getInviteToken(),
                entity.getAttendance(),
                entity.getName(),
                entity.getCompanionsText(),
                allergiesList
        );
    }
}
