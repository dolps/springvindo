package com.dolp.vindo;

import com.dolp.vindo.domain.SurfSpot;
import com.dolp.vindo.repository.SurfSpotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class Dummydata implements ApplicationRunner {
    @Autowired
    private SurfSpotRepository repository;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        SurfSpot verket = new SurfSpot();
        verket.setId(1L);
        verket.setName("Verkest");
        verket.setLatitude(59.612221);
        verket.longitude(10.413914);
        repository.save(verket);

        SurfSpot snaroy = new SurfSpot();
        snaroy.setId(2L);
        snaroy.setName("Snarøya");
        snaroy.setLatitude(59.888479);
        snaroy.longitude(10.634014);

        repository.save(snaroy);
    }
}
