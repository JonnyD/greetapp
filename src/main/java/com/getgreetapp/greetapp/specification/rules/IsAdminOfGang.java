package com.getgreetapp.greetapp.specification.rules;

import com.getgreetapp.greetapp.domain.Gang;
import com.getgreetapp.greetapp.domain.GangUser;
import com.getgreetapp.greetapp.domain.User;
import com.getgreetapp.greetapp.specification.AbstractSpecification;

import java.util.Set;

public class IsAdminOfGang extends AbstractSpecification<Gang> {
    private User loggedInUser;

    public IsAdminOfGang(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public boolean isSatisfiedBy(Gang gang) {
        Set<GangUser> gangUsers = gang.getGangUsers();

        for (GangUser gangUser : gangUsers) {
            if (gangUser.isAdmin()) {
                return true;
            }
        }

        return false;
    }
}
