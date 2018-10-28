package com.getgreetapp.greetapp.specification.rules;

import com.getgreetapp.greetapp.domain.Greet;
import com.getgreetapp.greetapp.domain.User;
import com.getgreetapp.greetapp.specification.AbstractSpecification;

public class IsInvited extends AbstractSpecification<Greet> {
    private User loggedInUser;

    public IsInvited(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public boolean isSatisfiedBy(Greet greet) {
            return greet.isInvited(this.loggedInUser);
        }
}
