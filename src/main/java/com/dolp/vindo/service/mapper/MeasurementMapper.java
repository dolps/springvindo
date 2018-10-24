package com.dolp.vindo.service.mapper;

import com.dolp.vindo.domain.*;
import com.dolp.vindo.service.dto.MeasurementDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Measurement and its DTO MeasurementDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MeasurementMapper extends EntityMapper<MeasurementDTO, Measurement> {



    default Measurement fromId(Long id) {
        if (id == null) {
            return null;
        }
        Measurement measurement = new Measurement();
        measurement.setId(id);
        return measurement;
    }
}
