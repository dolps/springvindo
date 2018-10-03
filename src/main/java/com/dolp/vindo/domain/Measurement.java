package com.dolp.vindo.domain;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@XmlRootElement(name = "Measurement")
@XmlAccessorType(XmlAccessType.FIELD)
public class Measurement {
    @XmlElement(name = "DataID")
    private int dataId;
    @XmlElement(name = "StationID")
    private int stationId;
    @XmlElement(name = "Time")
    private Date time;
    @XmlElement(name = "WindAvg")
    private double windAvg;
    @XmlElement(name = "WindStDev")
    private double windStDev;
    @XmlElement(name = "WindMax")
    private double windMax;
    @XmlElement(name = "WindMin")
    private double windMin;
    @XmlElement(name = "DirectionAvg")
    private double directionAvg;
    @XmlElement(name = "DirectionVectorAvg")
    private double directionVectorAvg;
    @XmlElement(name = "DirectionStDev")
    private double directionStDev;
    @XmlElement(name = "Temperature1")
    private double temperature;


    /**
     * <Measurement>
     * <DataID>3971879</DataID>
     * <StationID>3</StationID>
     * <Time>2018-10-03T18:12:38+00:00</Time>
     * <WindAvg>3.1</WindAvg>
     * <WindVectorAvg>-999</WindVectorAvg>
     * <WindStDev>1.1</WindStDev>
     * <WindMax>6.1</WindMax>
     * <WindMin>1.5</WindMin>
     * <DirectionAvg>7</DirectionAvg>
     * <DirectionVectorAvg>7</DirectionVectorAvg>
     * <DirectionStDev>16.1</DirectionStDev>
     * <Temperature1>7.4</Temperature1>
     * <Temperature2>-999</Temperature2>
     * <Light>-999</Light>
     * <Battery>4.033</Battery>
     * <Pressure/>
     * <Humidity/>
     * </Measurement>
     */

    public Measurement() {
    }

    public int getDataId() {
        return dataId;
    }

    public void setDataId(int dataId) {
        this.dataId = dataId;
    }

    public int getStationId() {
        return stationId;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public double getWindAvg() {
        return windAvg;
    }

    public void setWindAvg(double windAvg) {
        this.windAvg = windAvg;
    }

    public double getWindStDev() {
        return windStDev;
    }

    public void setWindStDev(double windStDev) {
        this.windStDev = windStDev;
    }

    public double getWindMax() {
        return windMax;
    }

    public void setWindMax(double windMax) {
        this.windMax = windMax;
    }

    public double getWindMin() {
        return windMin;
    }

    public void setWindMin(double windMin) {
        this.windMin = windMin;
    }

    public double getDirectionAvg() {
        return directionAvg;
    }

    public void setDirectionAvg(double directionAvg) {
        this.directionAvg = directionAvg;
    }

    public double getDirectionVectorAvg() {
        return directionVectorAvg;
    }

    public void setDirectionVectorAvg(double directionVectorAvg) {
        this.directionVectorAvg = directionVectorAvg;
    }

    public double getDirectionStDev() {
        return directionStDev;
    }

    public void setDirectionStDev(double directionStDev) {
        this.directionStDev = directionStDev;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public void setStationId(int stationId) {

        this.stationId = stationId;
    }

    @Override
    public String toString() {
        return "Measurement{" +
            "dataId=" + dataId +
            ", stationId=" + stationId +
            ", time=" + time +
            ", windAvg=" + windAvg +
            ", windStDev=" + windStDev +
            ", windMax=" + windMax +
            ", windMin=" + windMin +
            ", directionAvg=" + directionAvg +
            ", directionVectorAvg=" + directionVectorAvg +
            ", directionStDev=" + directionStDev +
            ", temperature=" + temperature +
            '}';
    }


}
