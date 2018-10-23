package com.dolp.vindo.service;

import com.dolp.vindo.domain.SurfSpot;
import com.dolp.vindo.repository.SurfSpotRepository;
import com.dolp.vindo.service.dto.SurfSpotDTO;
import com.dolp.vindo.service.mapper.SurfSpotMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing SurfSpot.
 */
@Service
@Transactional
public class SurfSpotService {

    private final Logger log = LoggerFactory.getLogger(SurfSpotService.class);

    private final SurfSpotRepository surfSpotRepository;

    private final SurfSpotMapper surfSpotMapper;

    public SurfSpotService(SurfSpotRepository surfSpotRepository, SurfSpotMapper surfSpotMapper) {
        this.surfSpotRepository = surfSpotRepository;
        this.surfSpotMapper = surfSpotMapper;
    }

    /**
     * Save a surfSpot.
     *
     * @param surfSpotDTO the entity to save
     * @return the persisted entity
     */
    public SurfSpotDTO save(SurfSpotDTO surfSpotDTO) {
        log.debug("Request to save SurfSpot : {}", surfSpotDTO);
        SurfSpot surfSpot = surfSpotMapper.toEntity(surfSpotDTO);
        surfSpot = surfSpotRepository.save(surfSpot);
        return surfSpotMapper.toDto(surfSpot);
    }

    /**
     * Get all the surfSpots.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<SurfSpotDTO> findAll() {
        log.debug("Request to get all SurfSpots");
        return surfSpotRepository.findAll().stream()
            .map(surfSpotMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one surfSpot by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<SurfSpotDTO> findOne(Long id) {
        log.debug("Request to get SurfSpot : {}", id);
        return surfSpotRepository.findById(id)
            .map(surfSpotMapper::toDto);
    }

    /**
     * Delete the surfSpot by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete SurfSpot : {}", id);
        surfSpotRepository.deleteById(id);
    }
}
