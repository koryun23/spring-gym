package org.example.repository;

import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;
import org.example.entity.user.LoginAttemptEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginAttemptRepository extends JpaRepository<LoginAttemptEntity, Long> {

    @Modifying
    @Transactional
    @Query("update LoginAttemptEntity l set l.counter = l.counter + 1 where l.remoteAddress = ?1")
    void incrementCounter(String remoteAddress);

    @Modifying
    @Transactional
    @Query("update LoginAttemptEntity l set l.counter = 0 where l.remoteAddress= ?1")
    LoginAttemptEntity reset(String remoteAddress);

    @Modifying
    @Transactional
    @Query("update LoginAttemptEntity l set l.counter = ?2, l.blockedUntil = ?3 where l.remoteAddress = ?1")
    LoginAttemptEntity update(String remoteAddress, Integer counter, LocalDateTime blockedUntil);

    Optional<LoginAttemptEntity> findByRemoteAddress(String remoteAddress);
}
