package com.omaru.wedding.controller;

import com.omaru.wedding.dto.InviteResponseDto;
import com.omaru.wedding.dto.InviteUpdateRequestDto;
import com.omaru.wedding.service.InviteService;
import com.omaru.wedding.view.InviteView;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/invites")
@RequiredArgsConstructor

public class InviteController {

    private final InviteService inviteService;

    //GET
    @GetMapping("/{token}")
    public InviteResponseDto get(@PathVariable String token) {
        return InviteView.toDto(inviteService.getByTokenOrThrow(token));
    }

    //PATCH
    @PatchMapping("/{token}")
    public InviteResponseDto patch(@PathVariable String token, @Valid @RequestBody InviteUpdateRequestDto request) {

        var updated = inviteService.update(token,request);
        return InviteView.toDto(updated);

    }
}
