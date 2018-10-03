package com.dolp.vindo.domain;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@Data
@NoArgsConstructor
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
}
