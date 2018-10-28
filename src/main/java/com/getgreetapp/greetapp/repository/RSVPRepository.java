package com.getgreetapp.greetapp.repository;

import com.getgreetapp.greetapp.domain.RSVP;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the RSVP entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RSVPRepository extends JpaRepository<RSVP, Long> {

    @Query("select rsvp from RSVP rsvp where rsvp.user.login = ?#{principal.username}")
    List<RSVP> findByUserIsCurrentUser();

}
