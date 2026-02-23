package com.omaru.wedding.service;

import com.omaru.wedding.dto.InviteUpdateRequestDto;
import com.omaru.wedding.entity.InviteEntity;
import com.omaru.wedding.exception.InviteNotFoundException;
import com.omaru.wedding.repository.InviteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InviteService {
    private final InviteRepository inviteRepository;

    @Transactional(readOnly = true)
    public InviteEntity getByTokenOrThrow(String token) {
        return inviteRepository.findByInviteToken(token).orElseThrow(InviteNotFoundException::new);
    }
    @Transactional
    public InviteEntity update(String token, InviteUpdateRequestDto request) {
        var entity = getByTokenOrThrow(token);
        entity.setAttendance(request.attendance());
        entity.setCompanionsText(request.companionsText());

        return inviteRepository.save(entity);
    }
}
