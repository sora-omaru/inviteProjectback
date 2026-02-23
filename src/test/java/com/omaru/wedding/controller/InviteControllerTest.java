package com.omaru.wedding.controller;

import com.omaru.wedding.entity.InviteEntity;
import com.omaru.wedding.repository.InviteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * 最低限の結合テスト（Controller + Service + Repository + ExceptionHandlerまで通す）
 */
@SpringBootTest
@AutoConfigureMockMvc
class InviteControllerTest {

    @Autowired MockMvc mockMvc;
    @Autowired InviteRepository inviteRepository;

    private String token;

    @BeforeEach
    void setUp() {
        inviteRepository.deleteAll();

        InviteEntity e = new InviteEntity();
        e.setInviteToken("TEST_TOKEN_123");
        e.setAttendance((short) 0);
        e.setName(null);
        e.setCompanionsText(null);

        token = inviteRepository.save(e).getInviteToken();
    }

    @Test
    void get_ok_returnsDto() throws Exception {
        mockMvc.perform(get("/api/v1/invites/{token}", token))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.inviteToken").value(token))
                .andExpect(jsonPath("$.attendance").value(0));
    }

    @Test
    void get_notFound_returnsProblemDetail() throws Exception {
        mockMvc.perform(get("/api/v1/invites/{token}", "NOT_EXISTS"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.errorCode").value("INVITE_NOT_FOUND"))
                .andExpect(jsonPath("$.instance").value("/api/v1/invites/NOT_EXISTS"));
    }

    @Test
    void patch_invalidBody_returnsProblemDetail() throws Exception {
        // JSON壊れ（request-body-invalid 400）を確認
        mockMvc.perform(patch("/api/v1/invites/{token}", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"attendance\":1,\"companionsText\":"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.errorCode").value("REQUEST_BODY_INVALID"));
    }
}