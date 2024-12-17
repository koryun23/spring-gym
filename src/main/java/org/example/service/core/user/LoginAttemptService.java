package org.example.service.core.user;

import java.util.Optional;
import org.example.entity.user.LoginAttemptEntity;

public interface LoginAttemptService {

    LoginAttemptEntity create(LoginAttemptEntity loginAttemptEntity);

    void incrementCounter(String remoteAddress);

    void reset(String remoteAddress);

    Optional<LoginAttemptEntity> findByRemoteAddress(String remoteAddress);

    void update(LoginAttemptEntity loginAttemptEntity);
}
