package org.example.service.impl.user;

import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.user.LoginAttemptEntity;
import org.example.repository.LoginAttemptRepository;
import org.example.service.core.user.LoginAttemptService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
@Slf4j
public class LoginAttemptServiceImpl implements LoginAttemptService {

    private LoginAttemptRepository loginAttemptRepository;

    public LoginAttemptServiceImpl(LoginAttemptRepository loginAttemptRepository) {
        this.loginAttemptRepository = loginAttemptRepository;
    }

    @Override
    public LoginAttemptEntity create(LoginAttemptEntity loginAttemptEntity) {
        Assert.notNull(loginAttemptEntity, "Login attempt must not be null");
        log.info("Creating a new login attempt - {}", loginAttemptEntity);
        LoginAttemptEntity savedLoginAttemptEntity = loginAttemptRepository.save(loginAttemptEntity);
        log.info("Successfully created a new login attempt entity - {}", savedLoginAttemptEntity);
        return savedLoginAttemptEntity;
    }

    @Transactional
    @Override
    public void incrementCounter(String remoteAddress) {
        Assert.notNull(remoteAddress, "Remote address must not be null");
        Assert.hasText(remoteAddress, "Remote address must not be empty");
        log.info("Incrementing the attempt counter for login attempts from {}", remoteAddress);
        loginAttemptRepository.incrementCounter(remoteAddress);
        log.info("Successfully incremented the attempt counter for login attempts from {}", remoteAddress);
    }

    @Transactional
    @Override
    public void reset(String remoteAddress) {
        Assert.notNull(remoteAddress, "Remote address must not be null");
        Assert.hasText(remoteAddress, "Remote address must not be empty");
        log.info("Resetting login attempts from {}", remoteAddress);
        loginAttemptRepository.reset(remoteAddress);
        log.info("Successfully reset the attempt counter for login attempts from {}", remoteAddress);
    }

    @Override
    public Optional<LoginAttemptEntity> findByRemoteAddress(String remoteAddress) {
        Assert.notNull(remoteAddress, "Remote address must not be null");
        Assert.hasText(remoteAddress, "Remote address must not be empty");
        log.info("Retrieving login attempt entity with remote address {}", remoteAddress);
        Optional<LoginAttemptEntity> optionalLoginAttempt = loginAttemptRepository.findByRemoteAddress(remoteAddress);
        log.info("Successfully retrieved a login attempt entity - {}", optionalLoginAttempt);
        return optionalLoginAttempt;
    }

    @Transactional
    @Override
    public void update(LoginAttemptEntity loginAttemptEntity) {
        Assert.notNull(loginAttemptEntity, "Login attempt entity must not be null");
        log.info("Updating the login attempt to be {}", loginAttemptEntity);
        loginAttemptRepository.update(
            loginAttemptEntity.getRemoteAddress(),
            loginAttemptEntity.getCounter(),
            loginAttemptEntity.getBlockedUntil()
        );
        log.info("Successfully updated the login attempt to be {}", loginAttemptEntity);
    }


}
