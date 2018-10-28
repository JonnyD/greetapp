package com.getgreetapp.greetapp.specification.rules;

import com.getgreetapp.greetapp.domain.GreetInvitation;
import com.getgreetapp.greetapp.domain.User;
import com.getgreetapp.greetapp.repository.UserRepository;
import com.getgreetapp.greetapp.security.SecurityUtils;
import com.getgreetapp.greetapp.specification.AbstractSpecification;

import java.util.Optional;

public class CanCreateUpdateGreetInvitation extends AbstractSpecification<GreetInvitation> {
    private User loggedInUser;

    public CanCreateUpdateGreetInvitation(UserRepository userRepository) {
        Optional<String> login = SecurityUtils.getCurrentUserLogin();
        this.loggedInUser = userRepository.findOneByLogin(login.get()).get();
    }

    public boolean isSatisfiedBy(GreetInvitation greetInvitation) {
        IsHostOfGreet isHostOfGreet = new IsHostOfGreet(this.loggedInUser);

        return isHostOfGreet
            .isSatisfiedBy(greetInvitation.getGreet());
    }
}
