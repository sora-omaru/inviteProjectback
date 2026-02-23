package com.omaru.wedding.controller;

import com.omaru.wedding.dto.InvitePatchRequest;
import com.omaru.wedding.entity.InviteEntity;
import com.omaru.wedding.repository.InviteRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/invites")
@RequiredArgsConstructor

public class InviteController {
    private final InviteRepository inviteRepository;

    @PatchMapping("/{token}")
    public InviteEntity patchInvite(@PathVariable String token, @Valid @RequestBody InvitePatchRequest req) {
        InviteEntity entity = inviteRepository.findByInviteToken(token).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));//不明なtokenの場合は404を出す

        if (req.attendance() != null)
            entity.setAttendance(req.attendance());

        if (req.message() != null)
            entity.setMessage(req.message());

        return inviteRepository.save(entity);

    }
}
