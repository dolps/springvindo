package com.dolp.vindo.web.rest;

import com.dolp.vindo.VindoApp;

import com.dolp.vindo.domain.SurfSpot;
import com.dolp.vindo.domain.Measurement;
import com.dolp.vindo.repository.SurfSpotRepository;
import com.dolp.vindo.service.SurfSpotService;
import com.dolp.vindo.service.dto.SurfSpotDTO;
import com.dolp.vindo.service.mapper.SurfSpotMapper;
import com.dolp.vindo.web.rest.errors.ExceptionTranslator;
import com.dolp.vindo.service.dto.SurfSpotCriteria;
import com.dolp.vindo.service.SurfSpotQueryService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;


import static com.dolp.vindo.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the SurfSpotResource REST controller.
 *
 * @see SurfSpotResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VindoApp.class)
public class SurfSpotResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Double DEFAULT_LATITUDE = 1D;
    private static final Double UPDATED_LATITUDE = 2D;

    private static final Double DEFAULT_LONGITUDE = 1D;
    private static final Double UPDATED_LONGITUDE = 2D;

    private static final Integer DEFAULT_STATION_ID = 1;
    private static final Integer UPDATED_STATION_ID = 2;

    @Autowired
    private SurfSpotRepository surfSpotRepository;

    @Autowired
    private SurfSpotMapper surfSpotMapper;
    
    @Autowired
    private SurfSpotService surfSpotService;

    @Autowired
    private SurfSpotQueryService surfSpotQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSurfSpotMockMvc;

    private SurfSpot surfSpot;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SurfSpotResource surfSpotResource = new SurfSpotResource(surfSpotService, surfSpotQueryService);
        this.restSurfSpotMockMvc = MockMvcBuilders.standaloneSetup(surfSpotResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SurfSpot createEntity(EntityManager em) {
        SurfSpot surfSpot = new SurfSpot()
            .name(DEFAULT_NAME)
            .latitude(DEFAULT_LATITUDE)
            .longitude(DEFAULT_LONGITUDE)
            .stationId(DEFAULT_STATION_ID);
        return surfSpot;
    }

    @Before
    public void initTest() {
        surfSpot = createEntity(em);
    }

    @Test
    @Transactional
    public void createSurfSpot() throws Exception {
        int databaseSizeBeforeCreate = surfSpotRepository.findAll().size();

        // Create the SurfSpot
        SurfSpotDTO surfSpotDTO = surfSpotMapper.toDto(surfSpot);
        restSurfSpotMockMvc.perform(post("/api/surf-spots")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(surfSpotDTO)))
            .andExpect(status().isCreated());

        // Validate the SurfSpot in the database
        List<SurfSpot> surfSpotList = surfSpotRepository.findAll();
        assertThat(surfSpotList).hasSize(databaseSizeBeforeCreate + 1);
        SurfSpot testSurfSpot = surfSpotList.get(surfSpotList.size() - 1);
        assertThat(testSurfSpot.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSurfSpot.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testSurfSpot.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testSurfSpot.getStationId()).isEqualTo(DEFAULT_STATION_ID);
    }

    @Test
    @Transactional
    public void createSurfSpotWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = surfSpotRepository.findAll().size();

        // Create the SurfSpot with an existing ID
        surfSpot.setId(1L);
        SurfSpotDTO surfSpotDTO = surfSpotMapper.toDto(surfSpot);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSurfSpotMockMvc.perform(post("/api/surf-spots")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(surfSpotDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SurfSpot in the database
        List<SurfSpot> surfSpotList = surfSpotRepository.findAll();
        assertThat(surfSpotList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSurfSpots() throws Exception {
        // Initialize the database
        surfSpotRepository.saveAndFlush(surfSpot);

        // Get all the surfSpotList
        restSurfSpotMockMvc.perform(get("/api/surf-spots?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(surfSpot.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].stationId").value(hasItem(DEFAULT_STATION_ID)));
    }
    
    @Test
    @Transactional
    public void getSurfSpot() throws Exception {
        // Initialize the database
        surfSpotRepository.saveAndFlush(surfSpot);

        // Get the surfSpot
        restSurfSpotMockMvc.perform(get("/api/surf-spots/{id}", surfSpot.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(surfSpot.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE.doubleValue()))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE.doubleValue()))
            .andExpect(jsonPath("$.stationId").value(DEFAULT_STATION_ID));
    }

    @Test
    @Transactional
    public void getAllSurfSpotsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        surfSpotRepository.saveAndFlush(surfSpot);

        // Get all the surfSpotList where name equals to DEFAULT_NAME
        defaultSurfSpotShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the surfSpotList where name equals to UPDATED_NAME
        defaultSurfSpotShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllSurfSpotsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        surfSpotRepository.saveAndFlush(surfSpot);

        // Get all the surfSpotList where name in DEFAULT_NAME or UPDATED_NAME
        defaultSurfSpotShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the surfSpotList where name equals to UPDATED_NAME
        defaultSurfSpotShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllSurfSpotsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        surfSpotRepository.saveAndFlush(surfSpot);

        // Get all the surfSpotList where name is not null
        defaultSurfSpotShouldBeFound("name.specified=true");

        // Get all the surfSpotList where name is null
        defaultSurfSpotShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllSurfSpotsByLatitudeIsEqualToSomething() throws Exception {
        // Initialize the database
        surfSpotRepository.saveAndFlush(surfSpot);

        // Get all the surfSpotList where latitude equals to DEFAULT_LATITUDE
        defaultSurfSpotShouldBeFound("latitude.equals=" + DEFAULT_LATITUDE);

        // Get all the surfSpotList where latitude equals to UPDATED_LATITUDE
        defaultSurfSpotShouldNotBeFound("latitude.equals=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    public void getAllSurfSpotsByLatitudeIsInShouldWork() throws Exception {
        // Initialize the database
        surfSpotRepository.saveAndFlush(surfSpot);

        // Get all the surfSpotList where latitude in DEFAULT_LATITUDE or UPDATED_LATITUDE
        defaultSurfSpotShouldBeFound("latitude.in=" + DEFAULT_LATITUDE + "," + UPDATED_LATITUDE);

        // Get all the surfSpotList where latitude equals to UPDATED_LATITUDE
        defaultSurfSpotShouldNotBeFound("latitude.in=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    public void getAllSurfSpotsByLatitudeIsNullOrNotNull() throws Exception {
        // Initialize the database
        surfSpotRepository.saveAndFlush(surfSpot);

        // Get all the surfSpotList where latitude is not null
        defaultSurfSpotShouldBeFound("latitude.specified=true");

        // Get all the surfSpotList where latitude is null
        defaultSurfSpotShouldNotBeFound("latitude.specified=false");
    }

    @Test
    @Transactional
    public void getAllSurfSpotsByLongitudeIsEqualToSomething() throws Exception {
        // Initialize the database
        surfSpotRepository.saveAndFlush(surfSpot);

        // Get all the surfSpotList where longitude equals to DEFAULT_LONGITUDE
        defaultSurfSpotShouldBeFound("longitude.equals=" + DEFAULT_LONGITUDE);

        // Get all the surfSpotList where longitude equals to UPDATED_LONGITUDE
        defaultSurfSpotShouldNotBeFound("longitude.equals=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    public void getAllSurfSpotsByLongitudeIsInShouldWork() throws Exception {
        // Initialize the database
        surfSpotRepository.saveAndFlush(surfSpot);

        // Get all the surfSpotList where longitude in DEFAULT_LONGITUDE or UPDATED_LONGITUDE
        defaultSurfSpotShouldBeFound("longitude.in=" + DEFAULT_LONGITUDE + "," + UPDATED_LONGITUDE);

        // Get all the surfSpotList where longitude equals to UPDATED_LONGITUDE
        defaultSurfSpotShouldNotBeFound("longitude.in=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    public void getAllSurfSpotsByLongitudeIsNullOrNotNull() throws Exception {
        // Initialize the database
        surfSpotRepository.saveAndFlush(surfSpot);

        // Get all the surfSpotList where longitude is not null
        defaultSurfSpotShouldBeFound("longitude.specified=true");

        // Get all the surfSpotList where longitude is null
        defaultSurfSpotShouldNotBeFound("longitude.specified=false");
    }

    @Test
    @Transactional
    public void getAllSurfSpotsByStationIdIsEqualToSomething() throws Exception {
        // Initialize the database
        surfSpotRepository.saveAndFlush(surfSpot);

        // Get all the surfSpotList where stationId equals to DEFAULT_STATION_ID
        defaultSurfSpotShouldBeFound("stationId.equals=" + DEFAULT_STATION_ID);

        // Get all the surfSpotList where stationId equals to UPDATED_STATION_ID
        defaultSurfSpotShouldNotBeFound("stationId.equals=" + UPDATED_STATION_ID);
    }

    @Test
    @Transactional
    public void getAllSurfSpotsByStationIdIsInShouldWork() throws Exception {
        // Initialize the database
        surfSpotRepository.saveAndFlush(surfSpot);

        // Get all the surfSpotList where stationId in DEFAULT_STATION_ID or UPDATED_STATION_ID
        defaultSurfSpotShouldBeFound("stationId.in=" + DEFAULT_STATION_ID + "," + UPDATED_STATION_ID);

        // Get all the surfSpotList where stationId equals to UPDATED_STATION_ID
        defaultSurfSpotShouldNotBeFound("stationId.in=" + UPDATED_STATION_ID);
    }

    @Test
    @Transactional
    public void getAllSurfSpotsByStationIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        surfSpotRepository.saveAndFlush(surfSpot);

        // Get all the surfSpotList where stationId is not null
        defaultSurfSpotShouldBeFound("stationId.specified=true");

        // Get all the surfSpotList where stationId is null
        defaultSurfSpotShouldNotBeFound("stationId.specified=false");
    }

    @Test
    @Transactional
    public void getAllSurfSpotsByStationIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        surfSpotRepository.saveAndFlush(surfSpot);

        // Get all the surfSpotList where stationId greater than or equals to DEFAULT_STATION_ID
        defaultSurfSpotShouldBeFound("stationId.greaterOrEqualThan=" + DEFAULT_STATION_ID);

        // Get all the surfSpotList where stationId greater than or equals to UPDATED_STATION_ID
        defaultSurfSpotShouldNotBeFound("stationId.greaterOrEqualThan=" + UPDATED_STATION_ID);
    }

    @Test
    @Transactional
    public void getAllSurfSpotsByStationIdIsLessThanSomething() throws Exception {
        // Initialize the database
        surfSpotRepository.saveAndFlush(surfSpot);

        // Get all the surfSpotList where stationId less than or equals to DEFAULT_STATION_ID
        defaultSurfSpotShouldNotBeFound("stationId.lessThan=" + DEFAULT_STATION_ID);

        // Get all the surfSpotList where stationId less than or equals to UPDATED_STATION_ID
        defaultSurfSpotShouldBeFound("stationId.lessThan=" + UPDATED_STATION_ID);
    }


    @Test
    @Transactional
    public void getAllSurfSpotsByMeasurementsIsEqualToSomething() throws Exception {
        // Initialize the database
        Measurement measurements = MeasurementResourceIntTest.createEntity(em);
        em.persist(measurements);
        em.flush();
        surfSpot.addMeasurements(measurements);
        surfSpotRepository.saveAndFlush(surfSpot);
        Long measurementsId = measurements.getId();

        // Get all the surfSpotList where measurements equals to measurementsId
        defaultSurfSpotShouldBeFound("measurementsId.equals=" + measurementsId);

        // Get all the surfSpotList where measurements equals to measurementsId + 1
        defaultSurfSpotShouldNotBeFound("measurementsId.equals=" + (measurementsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultSurfSpotShouldBeFound(String filter) throws Exception {
        restSurfSpotMockMvc.perform(get("/api/surf-spots?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(surfSpot.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].stationId").value(hasItem(DEFAULT_STATION_ID)));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultSurfSpotShouldNotBeFound(String filter) throws Exception {
        restSurfSpotMockMvc.perform(get("/api/surf-spots?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingSurfSpot() throws Exception {
        // Get the surfSpot
        restSurfSpotMockMvc.perform(get("/api/surf-spots/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSurfSpot() throws Exception {
        // Initialize the database
        surfSpotRepository.saveAndFlush(surfSpot);

        int databaseSizeBeforeUpdate = surfSpotRepository.findAll().size();

        // Update the surfSpot
        SurfSpot updatedSurfSpot = surfSpotRepository.findById(surfSpot.getId()).get();
        // Disconnect from session so that the updates on updatedSurfSpot are not directly saved in db
        em.detach(updatedSurfSpot);
        updatedSurfSpot
            .name(UPDATED_NAME)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .stationId(UPDATED_STATION_ID);
        SurfSpotDTO surfSpotDTO = surfSpotMapper.toDto(updatedSurfSpot);

        restSurfSpotMockMvc.perform(put("/api/surf-spots")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(surfSpotDTO)))
            .andExpect(status().isOk());

        // Validate the SurfSpot in the database
        List<SurfSpot> surfSpotList = surfSpotRepository.findAll();
        assertThat(surfSpotList).hasSize(databaseSizeBeforeUpdate);
        SurfSpot testSurfSpot = surfSpotList.get(surfSpotList.size() - 1);
        assertThat(testSurfSpot.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSurfSpot.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testSurfSpot.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testSurfSpot.getStationId()).isEqualTo(UPDATED_STATION_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingSurfSpot() throws Exception {
        int databaseSizeBeforeUpdate = surfSpotRepository.findAll().size();

        // Create the SurfSpot
        SurfSpotDTO surfSpotDTO = surfSpotMapper.toDto(surfSpot);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSurfSpotMockMvc.perform(put("/api/surf-spots")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(surfSpotDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SurfSpot in the database
        List<SurfSpot> surfSpotList = surfSpotRepository.findAll();
        assertThat(surfSpotList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSurfSpot() throws Exception {
        // Initialize the database
        surfSpotRepository.saveAndFlush(surfSpot);

        int databaseSizeBeforeDelete = surfSpotRepository.findAll().size();

        // Get the surfSpot
        restSurfSpotMockMvc.perform(delete("/api/surf-spots/{id}", surfSpot.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SurfSpot> surfSpotList = surfSpotRepository.findAll();
        assertThat(surfSpotList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SurfSpot.class);
        SurfSpot surfSpot1 = new SurfSpot();
        surfSpot1.setId(1L);
        SurfSpot surfSpot2 = new SurfSpot();
        surfSpot2.setId(surfSpot1.getId());
        assertThat(surfSpot1).isEqualTo(surfSpot2);
        surfSpot2.setId(2L);
        assertThat(surfSpot1).isNotEqualTo(surfSpot2);
        surfSpot1.setId(null);
        assertThat(surfSpot1).isNotEqualTo(surfSpot2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SurfSpotDTO.class);
        SurfSpotDTO surfSpotDTO1 = new SurfSpotDTO();
        surfSpotDTO1.setId(1L);
        SurfSpotDTO surfSpotDTO2 = new SurfSpotDTO();
        assertThat(surfSpotDTO1).isNotEqualTo(surfSpotDTO2);
        surfSpotDTO2.setId(surfSpotDTO1.getId());
        assertThat(surfSpotDTO1).isEqualTo(surfSpotDTO2);
        surfSpotDTO2.setId(2L);
        assertThat(surfSpotDTO1).isNotEqualTo(surfSpotDTO2);
        surfSpotDTO1.setId(null);
        assertThat(surfSpotDTO1).isNotEqualTo(surfSpotDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(surfSpotMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(surfSpotMapper.fromId(null)).isNull();
    }
}
