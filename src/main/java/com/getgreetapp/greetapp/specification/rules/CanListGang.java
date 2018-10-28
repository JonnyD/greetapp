package com.getgreetapp.greetapp.specification.rules;

import com.getgreetapp.greetapp.domain.Gang;
import com.getgreetapp.greetapp.domain.User;
import com.getgreetapp.greetapp.repository.UserRepository;
import com.getgreetapp.greetapp.security.SecurityUtils;
import com.getgreetapp.greetapp.specification.AbstractSpecification;

import java.util.Optional;

public class CanListGang extends AbstractSpecification<Gang> {
    private User loggedInUser;

    public CanListGang(UserRepository userRepository) {
        Optional<String> login = SecurityUtils.getCurrentUserLogin();
        this.loggedInUser = userRepository.findOneByLogin(login.get()).get();
    }

    public boolean isSatisfiedBy(Gang gang) {
        IsGangPublic isGangPublic = new IsGangPublic();
        IsGangClosed isGangClosed = new IsGangClosed();
        IsMemberOfGang isMemberOfGang = new IsMemberOfGang(this.loggedInUser);

        return isGangPublic
            .or(isGangClosed)
            .or(isMemberOfGang)
            .isSatisfiedBy(gang);
    }
}
