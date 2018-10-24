package com.dolp.vindo.service.mapper;

import com.dolp.vindo.domain.*;
import com.dolp.vindo.service.dto.SurfSpotDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity SurfSpot and its DTO SurfSpotDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SurfSpotMapper extends EntityMapper<SurfSpotDTO, SurfSpot> {


    @Mapping(target = "measurements", ignore = true)
    SurfSpot toEntity(SurfSpotDTO surfSpotDTO);

    default SurfSpot fromId(Long id) {
        if (id == null) {
            return null;
        }
        SurfSpot surfSpot = new SurfSpot();
        surfSpot.setId(id);
        return surfSpot;
    }
}
