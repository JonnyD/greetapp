package com.getgreetapp.greetapp.specification.rules;

import com.getgreetapp.greetapp.domain.Gang;
import com.getgreetapp.greetapp.domain.User;
import com.getgreetapp.greetapp.specification.AbstractSpecification;

public class CanUpdateGang extends AbstractSpecification<Gang> {
    private User loggedInUser;

    public CanUpdateGang(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public boolean isSatisfiedBy(Gang gang) {
        IsAdminOfGang isAdminOfGang = new IsAdminOfGang(this.loggedInUser);

        return isAdminOfGang
            .isSatisfiedBy(gang);
    }
}
