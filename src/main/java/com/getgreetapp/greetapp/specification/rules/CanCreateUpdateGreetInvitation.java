package com.getgreetapp.greetapp.specification.rules;

import com.getgreetapp.greetapp.domain.GreetInvitation;
import com.getgreetapp.greetapp.domain.User;
import com.getgreetapp.greetapp.specification.AbstractSpecification;

public class CanCreateUpdateGreetInvitation extends AbstractSpecification<GreetInvitation> {
    private User loggedInUser;

    public CanCreateUpdateGreetInvitation(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public boolean isSatisfiedBy(GreetInvitation greetInvitation) {
        IsHostOfGreet isHostOfGreet = new IsHostOfGreet(this.loggedInUser);

        return isHostOfGreet
            .isSatisfiedBy(greetInvitation.getGreet());
    }
}
