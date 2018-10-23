package com.dolp.vindo.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.dolp.vindo.domain.SurfSpot;
import com.dolp.vindo.domain.*; // for static metamodels
import com.dolp.vindo.repository.SurfSpotRepository;
import com.dolp.vindo.service.dto.SurfSpotCriteria;
import com.dolp.vindo.service.dto.SurfSpotDTO;
import com.dolp.vindo.service.mapper.SurfSpotMapper;

/**
 * Service for executing complex queries for SurfSpot entities in the database.
 * The main input is a {@link SurfSpotCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SurfSpotDTO} or a {@link Page} of {@link SurfSpotDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SurfSpotQueryService extends QueryService<SurfSpot> {

    private final Logger log = LoggerFactory.getLogger(SurfSpotQueryService.class);

    private final SurfSpotRepository surfSpotRepository;

    private final SurfSpotMapper surfSpotMapper;

    public SurfSpotQueryService(SurfSpotRepository surfSpotRepository, SurfSpotMapper surfSpotMapper) {
        this.surfSpotRepository = surfSpotRepository;
        this.surfSpotMapper = surfSpotMapper;
    }

    /**
     * Return a {@link List} of {@link SurfSpotDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SurfSpotDTO> findByCriteria(SurfSpotCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SurfSpot> specification = createSpecification(criteria);
        return surfSpotMapper.toDto(surfSpotRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SurfSpotDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SurfSpotDTO> findByCriteria(SurfSpotCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SurfSpot> specification = createSpecification(criteria);
        return surfSpotRepository.findAll(specification, page)
            .map(surfSpotMapper::toDto);
    }

    /**
     * Function to convert SurfSpotCriteria to a {@link Specification}
     */
    private Specification<SurfSpot> createSpecification(SurfSpotCriteria criteria) {
        Specification<SurfSpot> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), SurfSpot_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), SurfSpot_.name));
            }
        }
        return specification;
    }
}
