package org.example.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import org.example.entity.user.LoginAttemptEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginAttemptRepository extends JpaRepository<LoginAttemptEntity, Long> {

    @Query("update LoginAttemptEntity l set l.counter = l.counter + 1 where l.remoteAddress = ?1")
    LoginAttemptEntity incrementCounter(String remoteAddress);

    @Query("update LoginAttemptEntity l set l.counter = 0 where l.remoteAddress= ?1")
    LoginAttemptEntity reset(String remoteAddress);

    @Query("update LoginAttemptEntity l set l.counter = ?2, l.blockedUntil = ?3 where l.remoteAddress = ?1")
    LoginAttemptEntity update(String remoteAddress, Integer counter, LocalDateTime blockedUntil);

    Optional<LoginAttemptEntity> findByRemoteAddress(String remoteAddress);
}
