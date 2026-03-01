package com.omaru.wedding.service;

import com.omaru.wedding.dto.AdminInviteListItemDto;
import com.omaru.wedding.repository.InviteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminInviteService {

    private final InviteRepository inviteRepository;

    @Transactional(readOnly = true)
    public List<AdminInviteListItemDto> getAllInvites() {

        return inviteRepository.findAllByOrderByUpdatedAtDesc()
                .stream()
                .map(entity -> new AdminInviteListItemDto(
                        entity.getInviteToken(),
                        entity.getName(),
                        entity.getAttendance(),
                        entity.getCompanionsText(),
                        entity.getKidsText(),
                        entity.getCreatedAt(),
                        entity.getUpdatedAt()
                ))
                .toList();
    }

}