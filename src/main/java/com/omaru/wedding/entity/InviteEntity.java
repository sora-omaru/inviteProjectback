package com.omaru.wedding.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.time.OffsetDateTime;

@Getter
@Setter
@Entity
@Table(name = "invites")
public class InviteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "invite_token",nullable = false,unique = true,length = 64)
    private String inviteToken;

    @Column(name = "name",length = 200)
    private String name;

    @Column(name = "attendance",nullable = false)
    private short attendance;

    @Column(name = "companions_text", columnDefinition = "text")
    private String companionsText;

    @Column(name = "kids_text",columnDefinition = "text")
    private String kidsText;

    @Column(name = "message_text",columnDefinition = "text")
    private String message;

    @Column(name = "created_at",nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at",nullable = false)
    private OffsetDateTime updatedAt;


    @PrePersist
    public void prePersist() {
        OffsetDateTime now = OffsetDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = OffsetDateTime.now();
    }

}

