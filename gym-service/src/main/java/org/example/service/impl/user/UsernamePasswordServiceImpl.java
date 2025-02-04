package org.example.service.impl.user;

import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.example.service.core.user.UserSuffixService;
import org.example.service.core.user.UsernamePasswordService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Slf4j
@Service
public class UsernamePasswordServiceImpl implements UsernamePasswordService {

    private final UserSuffixService userSuffixService;

    /**
     * Constructor.
     */
    public UsernamePasswordServiceImpl(UserSuffixService userSuffixService) {
        this.userSuffixService = userSuffixService;
    }

    /**
     * Method for generating a username for a user with the given first and last names.
     * The return format is as follows: {firstName}.{lastName}.{suffix}, where suffix is
     * an integer in case a user with the given firstName and lastName already exists.
     *
     * @param firstName String
     * @param lastName String
     * @return username String
     */
    @Override
    public String username(String firstName, String lastName) {
        Assert.notNull(firstName, "First Name must not be null");
        Assert.hasText(firstName, "First name must not be empty");
        Assert.notNull(lastName, "Last Name must not be null");
        Assert.hasText(lastName, "Last Name must not be empty");

        log.info("Generating a username for {} {}", firstName, lastName);

        Long suffix = userSuffixService.getSuffix(firstName, lastName);

        if (suffix == 1) {
            log.info("Successfully generated a username for {} {}", firstName, lastName);
            return firstName + "." + lastName;
        }
        log.info("Successfully generated a username for {} {}", firstName, lastName);
        return firstName + "." + lastName + "." + suffix;
    }

    /**
     * Method for generating a password. It is a random sequence of characters with a length of 10.
     *
     * @return password String
     */
    @Override
    public String password() {
        return UUID.randomUUID().toString().substring(0, 10);
    }
}
