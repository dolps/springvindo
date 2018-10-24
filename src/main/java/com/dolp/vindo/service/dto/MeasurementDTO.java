package com.dolp.vindo.service.dto;

import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Measurement entity.
 */
public class MeasurementDTO implements Serializable {

    private Long id;

    private Integer stationId;

    private Double windAvg;

    private Double windStDev;

    private Double windMax;

    private Double windMin;

    private Integer directionAverage;

    private Instant time;

    private Long surfSpotId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStationId() {
        return stationId;
    }

    public void setStationId(Integer stationId) {
        this.stationId = stationId;
    }

    public Double getWindAvg() {
        return windAvg;
    }

    public void setWindAvg(Double windAvg) {
        this.windAvg = windAvg;
    }

    public Double getWindStDev() {
        return windStDev;
    }

    public void setWindStDev(Double windStDev) {
        this.windStDev = windStDev;
    }

    public Double getWindMax() {
        return windMax;
    }

    public void setWindMax(Double windMax) {
        this.windMax = windMax;
    }

    public Double getWindMin() {
        return windMin;
    }

    public void setWindMin(Double windMin) {
        this.windMin = windMin;
    }

    public Integer getDirectionAverage() {
        return directionAverage;
    }

    public void setDirectionAverage(Integer directionAverage) {
        this.directionAverage = directionAverage;
    }

    public Instant getTime() {
        return time;
    }

    public void setTime(Instant time) {
        this.time = time;
    }

    public Long getSurfSpotId() {
        return surfSpotId;
    }

    public void setSurfSpotId(Long surfSpotId) {
        this.surfSpotId = surfSpotId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MeasurementDTO measurementDTO = (MeasurementDTO) o;
        if (measurementDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), measurementDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MeasurementDTO{" +
            "id=" + getId() +
            ", stationId=" + getStationId() +
            ", windAvg=" + getWindAvg() +
            ", windStDev=" + getWindStDev() +
            ", windMax=" + getWindMax() +
            ", windMin=" + getWindMin() +
            ", directionAverage=" + getDirectionAverage() +
            ", time='" + getTime() + "'" +
            ", surfSpot=" + getSurfSpotId() +
            "}";
    }
}
