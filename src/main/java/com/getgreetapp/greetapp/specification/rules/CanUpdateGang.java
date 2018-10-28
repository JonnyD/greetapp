package com.getgreetapp.greetapp.specification.rules;

import com.getgreetapp.greetapp.domain.Gang;
import com.getgreetapp.greetapp.domain.User;
import com.getgreetapp.greetapp.repository.UserRepository;
import com.getgreetapp.greetapp.security.SecurityUtils;
import com.getgreetapp.greetapp.specification.AbstractSpecification;

import java.util.Optional;

public class CanUpdateGang extends AbstractSpecification<Gang> {
    private User loggedInUser;

    public CanUpdateGang(UserRepository userRepository) {
        Optional<String> login = SecurityUtils.getCurrentUserLogin();
        this.loggedInUser = userRepository.findOneByLogin(login.get()).get();
    }

    public boolean isSatisfiedBy(Gang gang) {
        IsAdminOfGang isAdminOfGang = new IsAdminOfGang(this.loggedInUser);

        return isAdminOfGang
            .isSatisfiedBy(gang);
    }
}
