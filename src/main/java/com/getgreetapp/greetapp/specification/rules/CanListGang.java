package com.getgreetapp.greetapp.specification.rules;

import com.getgreetapp.greetapp.domain.Gang;
import com.getgreetapp.greetapp.domain.User;
import com.getgreetapp.greetapp.specification.AbstractSpecification;

public class CanListGang extends AbstractSpecification<Gang> {
    private User loggedInUser;

    public CanListGang(User loggedInUser) {
        this.loggedInUser = loggedInUser;
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
