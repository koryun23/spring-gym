package org.example.service.impl;

import org.example.service.core.UsernamePasswordService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


//TODO how i understand here TraineeUsernamePasswordServiceImpl and TrainerUsernamePasswordServiceImpl
// do the same thing if like this we can use UsernamePasswordService without its.
@Service("traineeUsernamePasswordService")
public class TraineeUsernamePasswordServiceImpl implements UsernamePasswordService {

    private final UsernamePasswordService usernamePasswordService;

    /**
     * Constructor.
     */
    public TraineeUsernamePasswordServiceImpl(@Qualifier("usernamePasswordService")
                                              UsernamePasswordService usernamePasswordService) {
        this.usernamePasswordService = usernamePasswordService;
    }

    @Override
    public String username(String firstName, String lastName, Long id, String uniqueSuffix) {
        return usernamePasswordService.username(firstName, lastName, id, uniqueSuffix);
    }

    @Override
    public String password() {
        return usernamePasswordService.password();
    }
}
