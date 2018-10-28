package com.getgreetapp.greetapp.repository;

import com.getgreetapp.greetapp.domain.GreetInvitation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the GreetInvitation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GreetInvitationRepository extends JpaRepository<GreetInvitation, Long> {

    @Query("select greet_invitation from GreetInvitation greet_invitation where greet_invitation.user.login = ?#{principal.username}")
    List<GreetInvitation> findByUserIsCurrentUser();

    @Query("select greet_invitation from GreetInvitation greet_invitation where user_id = ?1")
    List<GreetInvitation> findByUser(Long userId);

}
