package com.getgreetapp.greetapp.repository;

import com.getgreetapp.greetapp.domain.Gang;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Gang entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GangRepository extends JpaRepository<Gang, Long> {

}
