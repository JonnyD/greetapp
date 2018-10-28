package com.getgreetapp.greetapp.repository;

import com.getgreetapp.greetapp.domain.Greet;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Greet entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GreetRepository extends JpaRepository<Greet, Long> {

    @Query("select greet from Greet greet where greet.user.login = ?#{principal.username}")
    List<Greet> findByUserIsCurrentUser();

    @Query("select greet from Greet greet where user_id = ?1")
    List<Greet> findByUser(Long userId);

    @Query("select greet from Greet greet where group_id = ?1")
    List<Greet> findByGroup(Long groupId);

}
