package com.omaru.wedding.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.omaru.wedding.entity.InviteEntity;
import java.util.Optional;

public interface InviteRepository extends JpaRepository<InviteEntity,Long> {
    Optional<InviteEntity> findByInviteToken(String inviteToken);
}
