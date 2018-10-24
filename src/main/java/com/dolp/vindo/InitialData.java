package com.dolp.vindo;

import com.dolp.vindo.domain.Measurement;
import com.dolp.vindo.domain.SurfSpot;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Component
public class InitialData implements ApplicationRunner {
    private static final Logger log = LoggerFactory.getLogger(InitialData.class);

    @Autowired
    private SurfSpotRepository surfSpotRepository;
    @Autowired
    private MeasurementRepository measurementRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        fetchVindsidenData();

        surfSpotRepository.save(new SurfSpot()
            .name("Verket")
            .latitude(59.612221)
            .longitude(10.413914));

        surfSpotRepository.save(new SurfSpot()
            .name("Snarøya")
            .latitude(59.888479)
            .longitude(10.634014));


        measurementRepository.save(new Measurement().stationId(3).windAvg(2.9));
    }

    private void fetchVindsidenData() {
        RestTemplate template = new RestTemplate();
        VindSidenMeasurementResponse vindSidenMeasurementResponse =
            template.getForObject("http://vindsiden.no/xml.aspx?id=3", VindSidenMeasurementResponse.class);

        assert vindSidenMeasurementResponse != null;
        if (vindSidenMeasurementResponse.getVindSidenMeasurements() != null) {
            log.info("measurement: " + vindSidenMeasurementResponse.getVindSidenMeasurements().get(0).toString());
            log.info("norwegian direction: " + vindSidenMeasurementResponse.getVindSidenMeasurements().get(0).getNorwegianNameFromDirectionValue());
            Date d = vindSidenMeasurementResponse.getVindSidenMeasurements().get(0).getTime();
            measurementRepository.save(new Measurement().stationId(3).windAvg(2.9).time(d.toInstant()));
        } else {
            log.info("measurement not found");
        }
    }
}
