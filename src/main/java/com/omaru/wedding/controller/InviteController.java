package com.omaru.wedding.controller;

import com.omaru.wedding.entity.InviteEntity;
import com.omaru.wedding.repository.InviteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/invites")
@RequiredArgsConstructor

public class InviteController {
    private final InviteRepository inviteRepository;

    @GetMapping("/{token}")
    public InviteEntity getInvite(@PathVariable String token) {
        return inviteRepository.findByInviteToken(token)
                .orElseThrow();
    }
}
