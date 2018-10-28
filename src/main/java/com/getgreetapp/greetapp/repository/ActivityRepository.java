package com.getgreetapp.greetapp.repository;

import com.getgreetapp.greetapp.domain.Activity;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Activity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {

    @Query("select activity from Activity activity where activity.user.login = ?#{principal.username}")
    List<Activity> findByUserIsCurrentUser();

    @Query("select activity from Activity activity where user_id = ?1")
    List<Activity> findByUser(Long userId);
}
