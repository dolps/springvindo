package com.dolp.vindo.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the SurfSpot entity. This class is used in SurfSpotResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /surf-spots?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SurfSpotCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private DoubleFilter latitude;

    private DoubleFilter longitude;

    private IntegerFilter stationId;

    private LongFilter measurementsId;

    public SurfSpotCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public DoubleFilter getLatitude() {
        return latitude;
    }

    public void setLatitude(DoubleFilter latitude) {
        this.latitude = latitude;
    }

    public DoubleFilter getLongitude() {
        return longitude;
    }

    public void setLongitude(DoubleFilter longitude) {
        this.longitude = longitude;
    }

    public IntegerFilter getStationId() {
        return stationId;
    }

    public void setStationId(IntegerFilter stationId) {
        this.stationId = stationId;
    }

    public LongFilter getMeasurementsId() {
        return measurementsId;
    }

    public void setMeasurementsId(LongFilter measurementsId) {
        this.measurementsId = measurementsId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SurfSpotCriteria that = (SurfSpotCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(latitude, that.latitude) &&
            Objects.equals(longitude, that.longitude) &&
            Objects.equals(stationId, that.stationId) &&
            Objects.equals(measurementsId, that.measurementsId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        latitude,
        longitude,
        stationId,
        measurementsId
        );
    }

    @Override
    public String toString() {
        return "SurfSpotCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (latitude != null ? "latitude=" + latitude + ", " : "") +
                (longitude != null ? "longitude=" + longitude + ", " : "") +
                (stationId != null ? "stationId=" + stationId + ", " : "") +
                (measurementsId != null ? "measurementsId=" + measurementsId + ", " : "") +
            "}";
    }

}
