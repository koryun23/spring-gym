package org.example.service.core.user;

import java.util.Optional;
import org.example.entity.auth.LoginAttemptEntity;

public interface LoginAttemptService {

    LoginAttemptEntity create(LoginAttemptEntity loginAttemptEntity);

    Integer incrementCounter(String remoteAddress);

    void reset(String remoteAddress);

    Optional<LoginAttemptEntity> findByRemoteAddress(String remoteAddress);

    void update(LoginAttemptEntity loginAttemptEntity);
}
