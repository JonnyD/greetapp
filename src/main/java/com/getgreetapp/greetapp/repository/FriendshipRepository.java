package com.getgreetapp.greetapp.repository;

import com.getgreetapp.greetapp.domain.Friendship;
import com.getgreetapp.greetapp.domain.Greet;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Friendship entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Long> {

    @Query("select friendship from Friendship friendship where friendship.user.login = ?#{principal.username}")
    List<Friendship> findByUserIsCurrentUser();

    @Query("select friendship from Friendship friendship where user_id = ?1 or friend_id = ?1")
    List<Friendship> findByUser(Long userId);
}
