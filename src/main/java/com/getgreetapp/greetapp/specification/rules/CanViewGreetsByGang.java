package com.getgreetapp.greetapp.specification.rules;

import com.getgreetapp.greetapp.domain.Gang;
import com.getgreetapp.greetapp.domain.Greet;
import com.getgreetapp.greetapp.domain.GreetInvitation;
import com.getgreetapp.greetapp.domain.User;
import com.getgreetapp.greetapp.repository.UserRepository;
import com.getgreetapp.greetapp.security.SecurityUtils;
import com.getgreetapp.greetapp.specification.AbstractSpecification;

import java.util.Optional;

public class CanViewGreetsByGang extends AbstractSpecification<Greet> {
    private User loggedInUser;

    public CanViewGreetsByGang(UserRepository userRepository) {
        Optional<String> login = SecurityUtils.getCurrentUserLogin();
        this.loggedInUser = userRepository.findOneByLogin(login.get()).get();
    }

    public boolean isSatisfiedBy(Greet greet) {
        IsGangPublic isGangPublic = new IsGangPublic();
        IsHostOfGreet isHostOfGreet = new IsHostOfGreet(this.loggedInUser);
        IsMemberOfGang isMemberOfGang = new IsMemberOfGang(this.loggedInUser);

        boolean isGangPublicBoolean = isGangPublic
            .or(isMemberOfGang)
            .isSatisfiedBy(greet.getGang());

        boolean isHostOfGreetBoolean = isHostOfGreet
            .isSatisfiedBy(greet);

        return isGangPublicBoolean && isHostOfGreetBoolean;
    }
}
