package com.dolp.vindo.service.mapper;

import com.dolp.vindo.domain.*;
import com.dolp.vindo.service.dto.MeasurementDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Measurement and its DTO MeasurementDTO.
 */
@Mapper(componentModel = "spring", uses = {SurfSpotMapper.class})
public interface MeasurementMapper extends EntityMapper<MeasurementDTO, Measurement> {

    @Mapping(source = "surfSpot.id", target = "surfSpotId")
    MeasurementDTO toDto(Measurement measurement);

    @Mapping(source = "surfSpotId", target = "surfSpot")
    Measurement toEntity(MeasurementDTO measurementDTO);

    default Measurement fromId(Long id) {
        if (id == null) {
            return null;
        }
        Measurement measurement = new Measurement();
        measurement.setId(id);
        return measurement;
    }
}
