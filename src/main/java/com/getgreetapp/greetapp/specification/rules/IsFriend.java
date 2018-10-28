package com.getgreetapp.greetapp.specification.rules;

import com.getgreetapp.greetapp.domain.Greet;
import com.getgreetapp.greetapp.domain.User;
import com.getgreetapp.greetapp.service.FriendshipService;
import com.getgreetapp.greetapp.specification.AbstractSpecification;

public class IsFriend extends AbstractSpecification<Greet> {
    private User loggedInUser;

    private FriendshipService friendshipService;

    public IsFriend(User loggedInUser, FriendshipService friendshipService) {
        this.loggedInUser = loggedInUser;
        this.friendshipService = friendshipService;
    }

    public boolean isSatisfiedBy(Greet greet) {
        return greet.isFriendsOnly()
            && this.friendshipService.isFriend(this.loggedInUser, greet.getUser());
    }
}
