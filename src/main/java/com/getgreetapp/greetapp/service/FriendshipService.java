package com.getgreetapp.greetapp.service;

import com.getgreetapp.greetapp.domain.Friendship;
import com.getgreetapp.greetapp.domain.User;
import com.getgreetapp.greetapp.repository.FriendshipRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FriendshipService {
    private final FriendshipRepository friendshipRepository;

    public FriendshipService(FriendshipRepository friendshipRepository) {
        this.friendshipRepository = friendshipRepository;
    }

    public Optional<Friendship> getById(Long id) {
        return this.friendshipRepository.findById(id);
    }

    public List<User> getAllFriendsForUser(User user) {
        List<Friendship> friendships = this.friendshipRepository.findByUser(user.getId());

        List<User> friends = new ArrayList<User>();
        for (Friendship friendship : friendships) {
            if (friendship.getUser() == user) {
                friends.add(user);
            } else {
                friends.add(friendship.getFriend());
            }
        }

        return friends;
    }

    public boolean isFriend(User user, User friend) {
        List<User> friends = this.getAllFriendsForUser(user);
        for (User currentFriend : friends) {
            if (currentFriend == friend) {
                return true;
            }
        }
        return false;
    }
}
