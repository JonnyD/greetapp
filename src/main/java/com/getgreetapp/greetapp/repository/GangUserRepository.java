package com.getgreetapp.greetapp.repository;

import com.getgreetapp.greetapp.domain.GangUser;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the GangUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GangUserRepository extends JpaRepository<GangUser, Long> {

    @Query("select gang_user from GangUser gang_user where gang_user.user.login = ?#{principal.username}")
    List<GangUser> findByUserIsCurrentUser();

    @Query("select gang_user from GangUser gang_user where gang_id = ?1")
    List<GangUser> findByGroup(Long groupId);

    @Query("select gang_user from GangUser gang_user where gang_id = ?1 and user_id = ?2")
    GangUser findByGangAndUser(Long gangId, Long userId);

}
