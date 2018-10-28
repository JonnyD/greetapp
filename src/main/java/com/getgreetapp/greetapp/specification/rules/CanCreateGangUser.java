package com.getgreetapp.greetapp.specification.rules;

import com.getgreetapp.greetapp.domain.Gang;
import com.getgreetapp.greetapp.domain.GangUser;
import com.getgreetapp.greetapp.domain.User;
import com.getgreetapp.greetapp.repository.UserRepository;
import com.getgreetapp.greetapp.security.SecurityUtils;
import com.getgreetapp.greetapp.service.UserService;
import com.getgreetapp.greetapp.specification.AbstractSpecification;
import com.getgreetapp.greetapp.specification.SpecificationInterface;

import java.util.Optional;

public class CanCreateGangUser extends AbstractSpecification<GangUser> {
    private User loggedInUser;

    public CanCreateGangUser(UserService userService) {
        Optional<String> login = SecurityUtils.getCurrentUserLogin();
        this.loggedInUser = userService.getOneByLogin(login.get()).get();
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
