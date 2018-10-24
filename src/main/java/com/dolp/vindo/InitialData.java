package com.dolp.vindo;

import com.dolp.vindo.domain.Measurement;
import com.dolp.vindo.domain.SurfSpot;
import com.dolp.vindo.domain.VindSidenMeasurement;
import com.dolp.vindo.domain.VindSidenMeasurementResponse;
import com.dolp.vindo.repository.MeasurementRepository;
import com.dolp.vindo.repository.SurfSpotRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Component
public class InitialData implements ApplicationRunner {
    private static final Logger log = LoggerFactory.getLogger(InitialData.class);
    private static final String VINDSIDEN_URL = "http://vindsiden.no/xml.aspx?id=";

    @Autowired
    private SurfSpotRepository surfSpotRepository;
    @Autowired
    private MeasurementRepository measurementRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        SurfSpot verket = surfSpotRepository.save(new SurfSpot()
            .name("Verket")
            .stationId(3)
            .latitude(59.612221)
            .longitude(10.413914));

        List<VindSidenMeasurement> vindSidenMeasurements = fetchVindsidenMeasurements(3);
        List<Measurement> measurements = convertToMeasurements(vindSidenMeasurements);
        measurements.forEach(m -> m.setSurfSpot(verket));
        measurementRepository.saveAll(measurements);

        surfSpotRepository.save(new SurfSpot()
            .name("Snarøya")
            .latitude(59.888479)
            .longitude(10.634014));
    }

    private List<Measurement> convertToMeasurements(List<VindSidenMeasurement> vindSidenMeasurements) {
        List<Measurement> measurements = new ArrayList<>();
        vindSidenMeasurements.forEach(m -> {
            measurements.add(new Measurement().time(m.getTime().toInstant()).windAvg(m.getWindAvg()).directionAverage(m.getDirectionAvg()));
        });

        return measurements;
    }

    private List<VindSidenMeasurement> fetchVindsidenMeasurements(int stationId) {
        RestTemplate template = new RestTemplate();
        VindSidenMeasurementResponse vindSidenMeasurementResponse =
            template.getForObject(VINDSIDEN_URL + stationId, VindSidenMeasurementResponse.class);
        assert vindSidenMeasurementResponse != null;
        if (vindSidenMeasurementResponse.getVindSidenMeasurements() != null) {
            return vindSidenMeasurementResponse.getVindSidenMeasurements();
        } else {
            return null;
        }


    }
}
