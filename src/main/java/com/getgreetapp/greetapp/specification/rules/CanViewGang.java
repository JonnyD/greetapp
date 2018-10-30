package com.getgreetapp.greetapp.specification.rules;

import com.getgreetapp.greetapp.domain.Gang;
import com.getgreetapp.greetapp.domain.User;
import com.getgreetapp.greetapp.specification.AbstractSpecification;

public class CanViewGang extends AbstractSpecification<Gang> {
    private User loggedInUser;

    public CanViewGang(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public boolean isSatisfiedBy(Gang gang) {
        IsGangPublic isGangPublic = new IsGangPublic();
        IsMemberOfGang isMemberOfGang = new IsMemberOfGang(this.loggedInUser);

        return isGangPublic
            .or(isMemberOfGang)
            .isSatisfiedBy(gang);
    }
}
