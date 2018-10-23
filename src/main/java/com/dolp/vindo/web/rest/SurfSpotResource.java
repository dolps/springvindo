package com.dolp.vindo.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.dolp.vindo.service.SurfSpotService;
import com.dolp.vindo.web.rest.errors.BadRequestAlertException;
import com.dolp.vindo.web.rest.util.HeaderUtil;
import com.dolp.vindo.service.dto.SurfSpotDTO;
import com.dolp.vindo.service.dto.SurfSpotCriteria;
import com.dolp.vindo.service.SurfSpotQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing SurfSpot.
 */
@RestController
@RequestMapping("/api")
public class SurfSpotResource {

    private final Logger log = LoggerFactory.getLogger(SurfSpotResource.class);

    private static final String ENTITY_NAME = "surfSpot";

    private final SurfSpotService surfSpotService;

    private final SurfSpotQueryService surfSpotQueryService;

    public SurfSpotResource(SurfSpotService surfSpotService, SurfSpotQueryService surfSpotQueryService) {
        this.surfSpotService = surfSpotService;
        this.surfSpotQueryService = surfSpotQueryService;
    }

    /**
     * POST  /surf-spots : Create a new surfSpot.
     *
     * @param surfSpotDTO the surfSpotDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new surfSpotDTO, or with status 400 (Bad Request) if the surfSpot has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/surf-spots")
    @Timed
    public ResponseEntity<SurfSpotDTO> createSurfSpot(@RequestBody SurfSpotDTO surfSpotDTO) throws URISyntaxException {
        log.debug("REST request to save SurfSpot : {}", surfSpotDTO);
        if (surfSpotDTO.getId() != null) {
            throw new BadRequestAlertException("A new surfSpot cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SurfSpotDTO result = surfSpotService.save(surfSpotDTO);
        return ResponseEntity.created(new URI("/api/surf-spots/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /surf-spots : Updates an existing surfSpot.
     *
     * @param surfSpotDTO the surfSpotDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated surfSpotDTO,
     * or with status 400 (Bad Request) if the surfSpotDTO is not valid,
     * or with status 500 (Internal Server Error) if the surfSpotDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/surf-spots")
    @Timed
    public ResponseEntity<SurfSpotDTO> updateSurfSpot(@RequestBody SurfSpotDTO surfSpotDTO) throws URISyntaxException {
        log.debug("REST request to update SurfSpot : {}", surfSpotDTO);
        if (surfSpotDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SurfSpotDTO result = surfSpotService.save(surfSpotDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, surfSpotDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /surf-spots : get all the surfSpots.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of surfSpots in body
     */
    @GetMapping("/surf-spots")
    @Timed
    public ResponseEntity<List<SurfSpotDTO>> getAllSurfSpots(SurfSpotCriteria criteria) {
        log.debug("REST request to get SurfSpots by criteria: {}", criteria);
        List<SurfSpotDTO> entityList = surfSpotQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * GET  /surf-spots/:id : get the "id" surfSpot.
     *
     * @param id the id of the surfSpotDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the surfSpotDTO, or with status 404 (Not Found)
     */
    @GetMapping("/surf-spots/{id}")
    @Timed
    public ResponseEntity<SurfSpotDTO> getSurfSpot(@PathVariable Long id) {
        log.debug("REST request to get SurfSpot : {}", id);
        Optional<SurfSpotDTO> surfSpotDTO = surfSpotService.findOne(id);
        return ResponseUtil.wrapOrNotFound(surfSpotDTO);
    }

    /**
     * DELETE  /surf-spots/:id : delete the "id" surfSpot.
     *
     * @param id the id of the surfSpotDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/surf-spots/{id}")
    @Timed
    public ResponseEntity<Void> deleteSurfSpot(@PathVariable Long id) {
        log.debug("REST request to delete SurfSpot : {}", id);
        surfSpotService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
