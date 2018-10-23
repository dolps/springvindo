package com.dolp.vindo.repository;

import com.dolp.vindo.domain.SurfSpot;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SurfSpot entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SurfSpotRepository extends JpaRepository<SurfSpot, Long>, JpaSpecificationExecutor<SurfSpot> {

}
