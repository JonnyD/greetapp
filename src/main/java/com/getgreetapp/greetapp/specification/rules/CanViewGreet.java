package com.getgreetapp.greetapp.specification.rules;

import com.getgreetapp.greetapp.domain.Greet;
import com.getgreetapp.greetapp.domain.GreetInvitation;
import com.getgreetapp.greetapp.domain.User;
import com.getgreetapp.greetapp.repository.UserRepository;
import com.getgreetapp.greetapp.security.SecurityUtils;
import com.getgreetapp.greetapp.service.FriendshipService;
import com.getgreetapp.greetapp.specification.AbstractSpecification;

import java.util.Optional;

public class CanViewGreet extends AbstractSpecification<Greet> {
    private User loggedInUser;

    private FriendshipService friendshipService;

    public CanViewGreet(UserRepository userRepository,
                        FriendshipService friendshipService) {
        Optional<String> login = SecurityUtils.getCurrentUserLogin();
        this.loggedInUser = userRepository.findOneByLogin(login.get()).get();
        this.friendshipService = friendshipService;
    }

    public boolean isSatisfiedBy(Greet greet) {
        IsGreetPublic isGreetPublic = new IsGreetPublic();
        IsInvited isInvited = new IsInvited(this.loggedInUser);
        IsFriend isFriend = new IsFriend(this.loggedInUser, this.friendshipService);

        return isGreetPublic
            .or(isInvited)
            .or(isFriend)
            .isSatisfiedBy(greet);
    }
}
