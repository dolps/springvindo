package com.dolp.vindo.service;

import com.dolp.vindo.domain.Measurement;
import com.dolp.vindo.repository.MeasurementRepository;
import com.dolp.vindo.service.dto.MeasurementDTO;
import com.dolp.vindo.service.mapper.MeasurementMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Measurement.
 */
@Service
@Transactional
public class MeasurementService {

    private final Logger log = LoggerFactory.getLogger(MeasurementService.class);

    private final MeasurementRepository measurementRepository;

    private final MeasurementMapper measurementMapper;

    public MeasurementService(MeasurementRepository measurementRepository, MeasurementMapper measurementMapper) {
        this.measurementRepository = measurementRepository;
        this.measurementMapper = measurementMapper;
    }

    /**
     * Save a measurement.
     *
     * @param measurementDTO the entity to save
     * @return the persisted entity
     */
    public MeasurementDTO save(MeasurementDTO measurementDTO) {
        log.debug("Request to save Measurement : {}", measurementDTO);
        Measurement measurement = measurementMapper.toEntity(measurementDTO);
        measurement = measurementRepository.save(measurement);
        return measurementMapper.toDto(measurement);
    }

    /**
     * Get all the measurements.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MeasurementDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Measurements");
        return measurementRepository.findAll(pageable)
            .map(measurementMapper::toDto);
    }


    /**
     * Get one measurement by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<MeasurementDTO> findOne(Long id) {
        log.debug("Request to get Measurement : {}", id);
        return measurementRepository.findById(id)
            .map(measurementMapper::toDto);
    }

    /**
     * Delete the measurement by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Measurement : {}", id);
        measurementRepository.deleteById(id);
    }
}
