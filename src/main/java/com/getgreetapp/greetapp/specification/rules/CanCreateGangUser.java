package com.getgreetapp.greetapp.specification.rules;

import com.getgreetapp.greetapp.domain.Gang;
import com.getgreetapp.greetapp.domain.GangUser;
import com.getgreetapp.greetapp.domain.User;
import com.getgreetapp.greetapp.specification.AbstractSpecification;
public class CanCreateGangUser extends AbstractSpecification<GangUser> {
    private User loggedInUser;

    public CanCreateGangUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public boolean isSatisfiedBy(GangUser gangUser) {
        IsAdminOfGang isAdminOfGang = new IsAdminOfGang(this.loggedInUser);
        IsMemberOfGang isMemberOfGang = new IsMemberOfGang(this.loggedInUser);
        IsMembershipApprovalAny isMembershipApprovalAny = new IsMembershipApprovalAny();
        CanSetGangUserAsAdmin canSetGangUserAsAdmin = new CanSetGangUserAsAdmin(this.loggedInUser);

        Gang gang = gangUser.getGang();

        AbstractSpecification<Gang> composite = isMemberOfGang
            .and(isMembershipApprovalAny);

        boolean value = composite
            .or(isAdminOfGang)
            .isSatisfiedBy(gang);

        return value && canSetGangUserAsAdmin.isSatisfiedBy(gangUser);
    }
}
