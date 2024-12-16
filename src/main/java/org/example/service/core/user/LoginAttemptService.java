package org.example.service.core.user;

import java.util.Optional;
import org.example.entity.user.LoginAttemptEntity;

public interface LoginAttemptService {

    LoginAttemptEntity create(LoginAttemptEntity loginAttemptEntity);

    LoginAttemptEntity incrementCounter(String remoteAddress);

    LoginAttemptEntity reset(String remoteAddress);

    Optional<LoginAttemptEntity> findByRemoteAddress(String remoteAddress);

    LoginAttemptEntity update(LoginAttemptEntity loginAttemptEntity);
}
