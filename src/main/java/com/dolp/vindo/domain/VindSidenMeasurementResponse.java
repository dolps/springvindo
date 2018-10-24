package com.dolp.vindo.domain;


import lombok.Data;
import lombok.Getter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Data
@Getter
@XmlRootElement(name = "Data")
@XmlAccessorType(XmlAccessType.FIELD)
public class VindSidenMeasurementResponse {
    @XmlElement(name = "Measurement")
    private List<VindSidenMeasurement> vindSidenMeasurements;
}
