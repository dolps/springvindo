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
        repository.save(new SurfSpot()
            .name("Verket")
            .latitude(59.612221)
            .longitude(10.413914));

        repository.save(new SurfSpot()
            .name("Snarøya")
            .latitude(59.888479)
            .longitude(10.634014));
    }
}
