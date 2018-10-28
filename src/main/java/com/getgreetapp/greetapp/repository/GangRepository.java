package com.getgreetapp.greetapp.repository;

import com.getgreetapp.greetapp.domain.Gang;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;


/**
 * Spring Data  repository for the Gang entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GangRepository extends JpaRepository<Gang, Long> {
}
