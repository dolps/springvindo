package com.dolp.vindo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A SurfSpot.
 */
@Entity
@Table(name = "surf_spot")
public class SurfSpot implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "station_id")
    private Integer stationId;

    @OneToMany(mappedBy = "surfSpot")
    private Set<Measurement> measurements = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public SurfSpot name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLatitude() {
        return latitude;
    }

    public SurfSpot latitude(Double latitude) {
        this.latitude = latitude;
        return this;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public SurfSpot longitude(Double longitude) {
        this.longitude = longitude;
        return this;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Integer getStationId() {
        return stationId;
    }

    public SurfSpot stationId(Integer stationId) {
        this.stationId = stationId;
        return this;
    }

    public void setStationId(Integer stationId) {
        this.stationId = stationId;
    }

    public Set<Measurement> getMeasurements() {
        return measurements;
    }

    public SurfSpot measurements(Set<Measurement> measurements) {
        this.measurements = measurements;
        return this;
    }

    public SurfSpot addMeasurements(Measurement measurement) {
        this.measurements.add(measurement);
        measurement.setSurfSpot(this);
        return this;
    }

    public SurfSpot removeMeasurements(Measurement measurement) {
        this.measurements.remove(measurement);
        measurement.setSurfSpot(null);
        return this;
    }

    public void setMeasurements(Set<Measurement> measurements) {
        this.measurements = measurements;
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
        SurfSpot surfSpot = (SurfSpot) o;
        if (surfSpot.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), surfSpot.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SurfSpot{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", latitude=" + getLatitude() +
            ", longitude=" + getLongitude() +
            ", stationId=" + getStationId() +
            "}";
    }
}