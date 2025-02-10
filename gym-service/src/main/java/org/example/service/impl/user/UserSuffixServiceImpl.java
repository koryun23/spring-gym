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

    /**
     * Method for obtaining a suffix for the username of the user with given first name and last name.
     * Since different users can have the same first and last names, the suffix will make every username unique.
     *
     * @param firstName String
     * @param lastName  String
     * @return suffix Long
     */
    @Transactional
    @Override
    public Long getSuffix(String firstName, String lastName) {
        log.info("Getting a suffix for the username of {} {}", firstName, lastName);
        Optional<UserSuffixEntity> optionalUserSuffixEntity =
            userSuffixEntityRepository.findByFirstAndLastName(firstName, lastName);
        if (optionalUserSuffixEntity.isEmpty()) {
            log.info("Suffix for the username of {} {} is 1", firstName, lastName);
            log.info("Persisting UserSuffixEntity with firstName - {}, lastName - {}, suffix - {}", firstName, lastName,
                1L);
            userSuffixEntityRepository.save(new UserSuffixEntity(firstName, lastName, 1L));
            log.info("Successfully persisted UserSuffixEntity with firstName - {}, lastName - {}, suffix - {}",
                firstName, lastName, 1L);
            return 1L;
        }
        UserSuffixEntity userSuffixEntity = optionalUserSuffixEntity.get();
        Long suffix = userSuffixEntity.getSuffix() + 1;
        log.info("Suffix for the username of {} {} is {}", firstName, lastName, suffix);

        userSuffixEntity.setSuffix(suffix);
        log.info("Persisting UserSuffixEntity with firstName - {}, lastName - {}, suffix - {}", firstName, lastName,
            1L);
        userSuffixEntityRepository.save(userSuffixEntity);
        log.info("Successfully persisted UserSuffixEntity with firstName - {}, lastName - {}, suffix - {}",
            firstName, lastName, 1L);

        return suffix;
    }
}
