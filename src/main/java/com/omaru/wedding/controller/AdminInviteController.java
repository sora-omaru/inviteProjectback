package com.omaru.wedding.controller;

import com.omaru.wedding.dto.AdminInviteListItemDto;
import com.omaru.wedding.entity.InviteEntity;
import com.omaru.wedding.repository.InviteRepository;
import com.omaru.wedding.service.AdminInviteService;
import com.omaru.wedding.util.TokenGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//管理者用API
@RestController
@RequestMapping("/api/v1/admin/invites")
@RequiredArgsConstructor
public class AdminInviteController {
    private final InviteRepository inviteRepository;

    private final AdminInviteService adminInviteService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AdminInviteListItemDto> getAllInvites() {
        return adminInviteService.getAllInvites();
    }


    @PostMapping
    public InviteEntity create() {
        // token重複は極低確率だが、unique制約があるので一応リトライ
        for (int i = 0; i < 5; i++) {
            InviteEntity invite = new InviteEntity();
            invite.setInviteToken(TokenGenerator.generate());
            invite.setAttendance((short) 0);
            //todo DTO化
            try {
                return inviteRepository.save(invite);
            } catch (DataIntegrityViolationException e) {
//invite_tokenのuniqueで衝突したら再生してリトライ
            }
        }
        throw new IllegalStateException("Failed to generate unique invite token");
    }
}
