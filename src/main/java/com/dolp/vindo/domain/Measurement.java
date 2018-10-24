package com.dolp.vindo.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A Measurement.
 */
@Entity
@Table(name = "measurement")
public class Measurement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "station_id")
    private Integer stationId;

    @Column(name = "wind_avg")
    private Double windAvg;

    @Column(name = "wind_st_dev")
    private Double windStDev;

    @Column(name = "wind_max")
    private Double windMax;

    @Column(name = "wind_min")
    private Double windMin;

    @Column(name = "direction_average")
    private Integer directionAverage;

    @Column(name = "jhi_time")
    private Instant time;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStationId() {
        return stationId;
    }

    public Measurement stationId(Integer stationId) {
        this.stationId = stationId;
        return this;
    }

    public void setStationId(Integer stationId) {
        this.stationId = stationId;
    }

    public Double getWindAvg() {
        return windAvg;
    }

    public Measurement windAvg(Double windAvg) {
        this.windAvg = windAvg;
        return this;
    }

    public void setWindAvg(Double windAvg) {
        this.windAvg = windAvg;
    }

    public Double getWindStDev() {
        return windStDev;
    }

    public Measurement windStDev(Double windStDev) {
        this.windStDev = windStDev;
        return this;
    }

    public void setWindStDev(Double windStDev) {
        this.windStDev = windStDev;
    }

    public Double getWindMax() {
        return windMax;
    }

    public Measurement windMax(Double windMax) {
        this.windMax = windMax;
        return this;
    }

    public void setWindMax(Double windMax) {
        this.windMax = windMax;
    }

    public Double getWindMin() {
        return windMin;
    }

    public Measurement windMin(Double windMin) {
        this.windMin = windMin;
        return this;
    }

    public void setWindMin(Double windMin) {
        this.windMin = windMin;
    }

    public Integer getDirectionAverage() {
        return directionAverage;
    }

    public Measurement directionAverage(Integer directionAverage) {
        this.directionAverage = directionAverage;
        return this;
    }

    public void setDirectionAverage(Integer directionAverage) {
        this.directionAverage = directionAverage;
    }

    public Instant getTime() {
        return time;
    }

    public Measurement time(Instant time) {
        this.time = time;
        return this;
    }

    public void setTime(Instant time) {
        this.time = time;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Measurement measurement = (Measurement) o;
        if (measurement.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), measurement.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Measurement{" +
            "id=" + getId() +
            ", stationId=" + getStationId() +
            ", windAvg=" + getWindAvg() +
            ", windStDev=" + getWindStDev() +
            ", windMax=" + getWindMax() +
            ", windMin=" + getWindMin() +
            ", directionAverage=" + getDirectionAverage() +
            ", time='" + getTime() + "'" +
            "}";
    }
}
