package com.dolp.vindo.web.rest;

import com.dolp.vindo.VindoApp;

import com.dolp.vindo.domain.Measurement;
import com.dolp.vindo.repository.MeasurementRepository;
import com.dolp.vindo.service.MeasurementService;
import com.dolp.vindo.service.dto.MeasurementDTO;
import com.dolp.vindo.service.mapper.MeasurementMapper;
import com.dolp.vindo.web.rest.errors.ExceptionTranslator;

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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;


import static com.dolp.vindo.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the MeasurementResource REST controller.
 *
 * @see MeasurementResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VindoApp.class)
public class MeasurementResourceIntTest {

    private static final Integer DEFAULT_STATION_ID = 1;
    private static final Integer UPDATED_STATION_ID = 2;

    private static final Double DEFAULT_WIND_AVG = 1D;
    private static final Double UPDATED_WIND_AVG = 2D;

    private static final Double DEFAULT_WIND_ST_DEV = 1D;
    private static final Double UPDATED_WIND_ST_DEV = 2D;

    private static final Double DEFAULT_WIND_MAX = 1D;
    private static final Double UPDATED_WIND_MAX = 2D;

    private static final Double DEFAULT_WIND_MIN = 1D;
    private static final Double UPDATED_WIND_MIN = 2D;

    private static final Integer DEFAULT_DIRECTION_AVERAGE = 1;
    private static final Integer UPDATED_DIRECTION_AVERAGE = 2;

    private static final Instant DEFAULT_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private MeasurementRepository measurementRepository;

    @Autowired
    private MeasurementMapper measurementMapper;
    
    @Autowired
    private MeasurementService measurementService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMeasurementMockMvc;

    private Measurement measurement;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MeasurementResource measurementResource = new MeasurementResource(measurementService);
        this.restMeasurementMockMvc = MockMvcBuilders.standaloneSetup(measurementResource)
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
    public static Measurement createEntity(EntityManager em) {
        Measurement measurement = new Measurement()
            .stationId(DEFAULT_STATION_ID)
            .windAvg(DEFAULT_WIND_AVG)
            .windStDev(DEFAULT_WIND_ST_DEV)
            .windMax(DEFAULT_WIND_MAX)
            .windMin(DEFAULT_WIND_MIN)
            .directionAverage(DEFAULT_DIRECTION_AVERAGE)
            .time(DEFAULT_TIME);
        return measurement;
    }

    @Before
    public void initTest() {
        measurement = createEntity(em);
    }

    @Test
    @Transactional
    public void createMeasurement() throws Exception {
        int databaseSizeBeforeCreate = measurementRepository.findAll().size();

        // Create the Measurement
        MeasurementDTO measurementDTO = measurementMapper.toDto(measurement);
        restMeasurementMockMvc.perform(post("/api/measurements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(measurementDTO)))
            .andExpect(status().isCreated());

        // Validate the Measurement in the database
        List<Measurement> measurementList = measurementRepository.findAll();
        assertThat(measurementList).hasSize(databaseSizeBeforeCreate + 1);
        Measurement testMeasurement = measurementList.get(measurementList.size() - 1);
        assertThat(testMeasurement.getStationId()).isEqualTo(DEFAULT_STATION_ID);
        assertThat(testMeasurement.getWindAvg()).isEqualTo(DEFAULT_WIND_AVG);
        assertThat(testMeasurement.getWindStDev()).isEqualTo(DEFAULT_WIND_ST_DEV);
        assertThat(testMeasurement.getWindMax()).isEqualTo(DEFAULT_WIND_MAX);
        assertThat(testMeasurement.getWindMin()).isEqualTo(DEFAULT_WIND_MIN);
        assertThat(testMeasurement.getDirectionAverage()).isEqualTo(DEFAULT_DIRECTION_AVERAGE);
        assertThat(testMeasurement.getTime()).isEqualTo(DEFAULT_TIME);
    }

    @Test
    @Transactional
    public void createMeasurementWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = measurementRepository.findAll().size();

        // Create the Measurement with an existing ID
        measurement.setId(1L);
        MeasurementDTO measurementDTO = measurementMapper.toDto(measurement);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMeasurementMockMvc.perform(post("/api/measurements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(measurementDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Measurement in the database
        List<Measurement> measurementList = measurementRepository.findAll();
        assertThat(measurementList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMeasurements() throws Exception {
        // Initialize the database
        measurementRepository.saveAndFlush(measurement);

        // Get all the measurementList
        restMeasurementMockMvc.perform(get("/api/measurements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(measurement.getId().intValue())))
            .andExpect(jsonPath("$.[*].stationId").value(hasItem(DEFAULT_STATION_ID)))
            .andExpect(jsonPath("$.[*].windAvg").value(hasItem(DEFAULT_WIND_AVG.doubleValue())))
            .andExpect(jsonPath("$.[*].windStDev").value(hasItem(DEFAULT_WIND_ST_DEV.doubleValue())))
            .andExpect(jsonPath("$.[*].windMax").value(hasItem(DEFAULT_WIND_MAX.doubleValue())))
            .andExpect(jsonPath("$.[*].windMin").value(hasItem(DEFAULT_WIND_MIN.doubleValue())))
            .andExpect(jsonPath("$.[*].directionAverage").value(hasItem(DEFAULT_DIRECTION_AVERAGE)))
            .andExpect(jsonPath("$.[*].time").value(hasItem(DEFAULT_TIME.toString())));
    }
    
    @Test
    @Transactional
    public void getMeasurement() throws Exception {
        // Initialize the database
        measurementRepository.saveAndFlush(measurement);

        // Get the measurement
        restMeasurementMockMvc.perform(get("/api/measurements/{id}", measurement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(measurement.getId().intValue()))
            .andExpect(jsonPath("$.stationId").value(DEFAULT_STATION_ID))
            .andExpect(jsonPath("$.windAvg").value(DEFAULT_WIND_AVG.doubleValue()))
            .andExpect(jsonPath("$.windStDev").value(DEFAULT_WIND_ST_DEV.doubleValue()))
            .andExpect(jsonPath("$.windMax").value(DEFAULT_WIND_MAX.doubleValue()))
            .andExpect(jsonPath("$.windMin").value(DEFAULT_WIND_MIN.doubleValue()))
            .andExpect(jsonPath("$.directionAverage").value(DEFAULT_DIRECTION_AVERAGE))
            .andExpect(jsonPath("$.time").value(DEFAULT_TIME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMeasurement() throws Exception {
        // Get the measurement
        restMeasurementMockMvc.perform(get("/api/measurements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMeasurement() throws Exception {
        // Initialize the database
        measurementRepository.saveAndFlush(measurement);

        int databaseSizeBeforeUpdate = measurementRepository.findAll().size();

        // Update the measurement
        Measurement updatedMeasurement = measurementRepository.findById(measurement.getId()).get();
        // Disconnect from session so that the updates on updatedMeasurement are not directly saved in db
        em.detach(updatedMeasurement);
        updatedMeasurement
            .stationId(UPDATED_STATION_ID)
            .windAvg(UPDATED_WIND_AVG)
            .windStDev(UPDATED_WIND_ST_DEV)
            .windMax(UPDATED_WIND_MAX)
            .windMin(UPDATED_WIND_MIN)
            .directionAverage(UPDATED_DIRECTION_AVERAGE)
            .time(UPDATED_TIME);
        MeasurementDTO measurementDTO = measurementMapper.toDto(updatedMeasurement);

        restMeasurementMockMvc.perform(put("/api/measurements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(measurementDTO)))
            .andExpect(status().isOk());

        // Validate the Measurement in the database
        List<Measurement> measurementList = measurementRepository.findAll();
        assertThat(measurementList).hasSize(databaseSizeBeforeUpdate);
        Measurement testMeasurement = measurementList.get(measurementList.size() - 1);
        assertThat(testMeasurement.getStationId()).isEqualTo(UPDATED_STATION_ID);
        assertThat(testMeasurement.getWindAvg()).isEqualTo(UPDATED_WIND_AVG);
        assertThat(testMeasurement.getWindStDev()).isEqualTo(UPDATED_WIND_ST_DEV);
        assertThat(testMeasurement.getWindMax()).isEqualTo(UPDATED_WIND_MAX);
        assertThat(testMeasurement.getWindMin()).isEqualTo(UPDATED_WIND_MIN);
        assertThat(testMeasurement.getDirectionAverage()).isEqualTo(UPDATED_DIRECTION_AVERAGE);
        assertThat(testMeasurement.getTime()).isEqualTo(UPDATED_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingMeasurement() throws Exception {
        int databaseSizeBeforeUpdate = measurementRepository.findAll().size();

        // Create the Measurement
        MeasurementDTO measurementDTO = measurementMapper.toDto(measurement);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMeasurementMockMvc.perform(put("/api/measurements")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(measurementDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Measurement in the database
        List<Measurement> measurementList = measurementRepository.findAll();
        assertThat(measurementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMeasurement() throws Exception {
        // Initialize the database
        measurementRepository.saveAndFlush(measurement);

        int databaseSizeBeforeDelete = measurementRepository.findAll().size();

        // Get the measurement
        restMeasurementMockMvc.perform(delete("/api/measurements/{id}", measurement.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Measurement> measurementList = measurementRepository.findAll();
        assertThat(measurementList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Measurement.class);
        Measurement measurement1 = new Measurement();
        measurement1.setId(1L);
        Measurement measurement2 = new Measurement();
        measurement2.setId(measurement1.getId());
        assertThat(measurement1).isEqualTo(measurement2);
        measurement2.setId(2L);
        assertThat(measurement1).isNotEqualTo(measurement2);
        measurement1.setId(null);
        assertThat(measurement1).isNotEqualTo(measurement2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MeasurementDTO.class);
        MeasurementDTO measurementDTO1 = new MeasurementDTO();
        measurementDTO1.setId(1L);
        MeasurementDTO measurementDTO2 = new MeasurementDTO();
        assertThat(measurementDTO1).isNotEqualTo(measurementDTO2);
        measurementDTO2.setId(measurementDTO1.getId());
        assertThat(measurementDTO1).isEqualTo(measurementDTO2);
        measurementDTO2.setId(2L);
        assertThat(measurementDTO1).isNotEqualTo(measurementDTO2);
        measurementDTO1.setId(null);
        assertThat(measurementDTO1).isNotEqualTo(measurementDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(measurementMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(measurementMapper.fromId(null)).isNull();
    }
}
