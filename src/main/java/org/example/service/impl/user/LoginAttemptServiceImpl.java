package org.example.service.impl.user;

import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.user.LoginAttemptEntity;
import org.example.exception.LoginAttemptNotFoundException;
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

    @Override
    public LoginAttemptEntity incrementCounter(String remoteAddress) {
        Assert.notNull(remoteAddress, "Remote address must not be null");
        Assert.hasText(remoteAddress, "Remote address must not be empty");
        log.info("Incrementing the attempt counter for login attempts from {}", remoteAddress);
        LoginAttemptEntity loginAttemptEntity = loginAttemptRepository.findByRemoteAddress(remoteAddress)
            .orElseThrow(() -> new LoginAttemptNotFoundException(remoteAddress));
        loginAttemptEntity.setCounter(loginAttemptEntity.getCounter() + 1);
        loginAttemptRepository.save(loginAttemptEntity); // TODO: USE AN UPDATE QUERY
        log.info("Successfully incremented the attempt counter for login attempts from {}, result - {}", remoteAddress,
            loginAttemptEntity);
        return loginAttemptEntity;
    }

    @Override
    public LoginAttemptEntity reset(String remoteAddress) {
        Assert.notNull(remoteAddress, "Remote address must not be null");
        Assert.hasText(remoteAddress, "Remote address must not be empty");
        log.info("Resetting login attempts from {}", remoteAddress);
        LoginAttemptEntity loginAttemptEntity = loginAttemptRepository.reset(remoteAddress);
        log.info("Successfully reset the attempt counter for login attempts from {}, result - {}", remoteAddress,
            loginAttemptEntity);
        return loginAttemptEntity;
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

    @Override
    public LoginAttemptEntity update(LoginAttemptEntity loginAttemptEntity) {
        LoginAttemptEntity persisted =
            loginAttemptRepository.findByRemoteAddress(loginAttemptEntity.getRemoteAddress())
                .orElseThrow(() -> new LoginAttemptNotFoundException(loginAttemptEntity.getRemoteAddress()));
        persisted.setCounter(loginAttemptEntity.getCounter());
        persisted.setBlockedUntil(loginAttemptEntity.getBlockedUntil());
        return loginAttemptRepository.save(persisted); // TODO: USE AN UPDATE QUERY
    }


}
