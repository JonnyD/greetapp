package com.getgreetapp.greetapp.specification.rules;

import com.getgreetapp.greetapp.domain.Gang;
import com.getgreetapp.greetapp.domain.GangUser;
import com.getgreetapp.greetapp.domain.User;
import com.getgreetapp.greetapp.security.SecurityUtils;
import com.getgreetapp.greetapp.service.UserService;
import com.getgreetapp.greetapp.specification.AbstractSpecification;

public class CanSetGangUserAsAdmin extends AbstractSpecification<GangUser> {
    private User loggedInUser;

    public CanSetGangUserAsAdmin(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public boolean isSatisfiedBy(GangUser gangUser) {
        IsAdminOfGang isAdminOfGang = new IsAdminOfGang(this.loggedInUser);

        return isAdminOfGang
            .isSatisfiedBy(gangUser.getGang()) && gangUser.getRole().equalsIgnoreCase(GangUser.Role.ADMIN.toString());
    }
}
