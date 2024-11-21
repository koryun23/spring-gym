package org.example.service.impl.user;

import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.user.UserSuffixEntity;
import org.example.repository.UserSuffixEntityRepository;
import org.example.service.core.user.UserSuffixService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class UserSuffixServiceImpl implements UserSuffixService {

    private final UserSuffixEntityRepository userSuffixEntityRepository;

    public UserSuffixServiceImpl(UserSuffixEntityRepository userSuffixEntityRepository) {
        this.userSuffixEntityRepository = userSuffixEntityRepository;
    }

    @Transactional
    @Override
    public Long getSuffix(String firstName, String lastName) {
        log.info("Getting a suffix for the username of {} {}", firstName, lastName);
        Optional<UserSuffixEntity> optionalUserSuffixEntity =
            userSuffixEntityRepository.findByFirstAndLastName(firstName, lastName);
        if (optionalUserSuffixEntity.isEmpty()) {
            userSuffixEntityRepository.save(new UserSuffixEntity(firstName, lastName, 1L));
            return 1L;
        }
        UserSuffixEntity userSuffixEntity = optionalUserSuffixEntity.get();
        Long suffix = userSuffixEntity.getSuffix() + 1;

        userSuffixEntity.setSuffix(suffix);
        userSuffixEntityRepository.save(userSuffixEntity);

        log.info("Suffix for the username of {} {} is {}", firstName, lastName, suffix);
        return suffix;
    }
}
