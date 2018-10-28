package com.getgreetapp.greetapp.specification.rules;

import com.getgreetapp.greetapp.domain.Greet;
import com.getgreetapp.greetapp.domain.User;
import com.getgreetapp.greetapp.specification.AbstractSpecification;

public class IsHostOfGreet extends AbstractSpecification<Greet> {
    private User loggedInUser;

    public IsHostOfGreet(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public boolean isSatisfiedBy(Greet greet) {
        if (greet.getUser().getId() == this.loggedInUser.getId()) {
            return true;
        }
        return false;
    }
}
