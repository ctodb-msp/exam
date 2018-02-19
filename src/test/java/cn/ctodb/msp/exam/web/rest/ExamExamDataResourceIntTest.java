package cn.ctodb.msp.exam.web.rest;

import cn.ctodb.msp.exam.ExamApp;

import cn.ctodb.msp.exam.config.SecurityBeanOverrideConfiguration;

import cn.ctodb.msp.exam.domain.ExamExamData;
import cn.ctodb.msp.exam.repository.ExamExamDataRepository;
import cn.ctodb.msp.exam.service.ExamExamDataService;
import cn.ctodb.msp.exam.service.dto.ExamExamDataDTO;
import cn.ctodb.msp.exam.service.mapper.ExamExamDataMapper;
import cn.ctodb.msp.exam.web.rest.errors.ExceptionTranslator;

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
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static cn.ctodb.msp.exam.web.rest.TestUtil.sameInstant;
import static cn.ctodb.msp.exam.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import cn.ctodb.msp.exam.domain.enumeration.Status;
/**
 * Test class for the ExamExamDataResource REST controller.
 *
 * @see ExamExamDataResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ExamApp.class, SecurityBeanOverrideConfiguration.class})
public class ExamExamDataResourceIntTest {

    private static final ZonedDateTime DEFAULT_START_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_START_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_END_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_END_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Integer DEFAULT_SCORE = 1;
    private static final Integer UPDATED_SCORE = 2;

    private static final Status DEFAULT_STATUS = Status.ENABLE;
    private static final Status UPDATED_STATUS = Status.DISABLE;

    private static final String DEFAULT_DATA = "AAAAAAAAAA";
    private static final String UPDATED_DATA = "BBBBBBBBBB";

    private static final String DEFAULT_CHECK = "AAAAAAAAAA";
    private static final String UPDATED_CHECK = "BBBBBBBBBB";

    @Autowired
    private ExamExamDataRepository examExamDataRepository;

    @Autowired
    private ExamExamDataMapper examExamDataMapper;

    @Autowired
    private ExamExamDataService examExamDataService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restExamExamDataMockMvc;

    private ExamExamData examExamData;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ExamExamDataResource examExamDataResource = new ExamExamDataResource(examExamDataService);
        this.restExamExamDataMockMvc = MockMvcBuilders.standaloneSetup(examExamDataResource)
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
    public static ExamExamData createEntity(EntityManager em) {
        ExamExamData examExamData = new ExamExamData()
            .startTime(DEFAULT_START_TIME)
            .endTime(DEFAULT_END_TIME)
            .score(DEFAULT_SCORE)
            .status(DEFAULT_STATUS)
            .data(DEFAULT_DATA)
            .check(DEFAULT_CHECK);
        return examExamData;
    }

    @Before
    public void initTest() {
        examExamData = createEntity(em);
    }

    @Test
    @Transactional
    public void createExamExamData() throws Exception {
        int databaseSizeBeforeCreate = examExamDataRepository.findAll().size();

        // Create the ExamExamData
        ExamExamDataDTO examExamDataDTO = examExamDataMapper.toDto(examExamData);
        restExamExamDataMockMvc.perform(post("/api/exam-exam-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(examExamDataDTO)))
            .andExpect(status().isCreated());

        // Validate the ExamExamData in the database
        List<ExamExamData> examExamDataList = examExamDataRepository.findAll();
        assertThat(examExamDataList).hasSize(databaseSizeBeforeCreate + 1);
        ExamExamData testExamExamData = examExamDataList.get(examExamDataList.size() - 1);
        assertThat(testExamExamData.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testExamExamData.getEndTime()).isEqualTo(DEFAULT_END_TIME);
        assertThat(testExamExamData.getScore()).isEqualTo(DEFAULT_SCORE);
        assertThat(testExamExamData.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testExamExamData.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testExamExamData.getCheck()).isEqualTo(DEFAULT_CHECK);
    }

    @Test
    @Transactional
    public void createExamExamDataWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = examExamDataRepository.findAll().size();

        // Create the ExamExamData with an existing ID
        examExamData.setId(1L);
        ExamExamDataDTO examExamDataDTO = examExamDataMapper.toDto(examExamData);

        // An entity with an existing ID cannot be created, so this API call must fail
        restExamExamDataMockMvc.perform(post("/api/exam-exam-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(examExamDataDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ExamExamData in the database
        List<ExamExamData> examExamDataList = examExamDataRepository.findAll();
        assertThat(examExamDataList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllExamExamData() throws Exception {
        // Initialize the database
        examExamDataRepository.saveAndFlush(examExamData);

        // Get all the examExamDataList
        restExamExamDataMockMvc.perform(get("/api/exam-exam-data?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(examExamData.getId().intValue())))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(sameInstant(DEFAULT_START_TIME))))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(sameInstant(DEFAULT_END_TIME))))
            .andExpect(jsonPath("$.[*].score").value(hasItem(DEFAULT_SCORE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA.toString())))
            .andExpect(jsonPath("$.[*].check").value(hasItem(DEFAULT_CHECK.toString())));
    }

    @Test
    @Transactional
    public void getExamExamData() throws Exception {
        // Initialize the database
        examExamDataRepository.saveAndFlush(examExamData);

        // Get the examExamData
        restExamExamDataMockMvc.perform(get("/api/exam-exam-data/{id}", examExamData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(examExamData.getId().intValue()))
            .andExpect(jsonPath("$.startTime").value(sameInstant(DEFAULT_START_TIME)))
            .andExpect(jsonPath("$.endTime").value(sameInstant(DEFAULT_END_TIME)))
            .andExpect(jsonPath("$.score").value(DEFAULT_SCORE))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.data").value(DEFAULT_DATA.toString()))
            .andExpect(jsonPath("$.check").value(DEFAULT_CHECK.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingExamExamData() throws Exception {
        // Get the examExamData
        restExamExamDataMockMvc.perform(get("/api/exam-exam-data/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExamExamData() throws Exception {
        // Initialize the database
        examExamDataRepository.saveAndFlush(examExamData);
        int databaseSizeBeforeUpdate = examExamDataRepository.findAll().size();

        // Update the examExamData
        ExamExamData updatedExamExamData = examExamDataRepository.findOne(examExamData.getId());
        // Disconnect from session so that the updates on updatedExamExamData are not directly saved in db
        em.detach(updatedExamExamData);
        updatedExamExamData
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME)
            .score(UPDATED_SCORE)
            .status(UPDATED_STATUS)
            .data(UPDATED_DATA)
            .check(UPDATED_CHECK);
        ExamExamDataDTO examExamDataDTO = examExamDataMapper.toDto(updatedExamExamData);

        restExamExamDataMockMvc.perform(put("/api/exam-exam-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(examExamDataDTO)))
            .andExpect(status().isOk());

        // Validate the ExamExamData in the database
        List<ExamExamData> examExamDataList = examExamDataRepository.findAll();
        assertThat(examExamDataList).hasSize(databaseSizeBeforeUpdate);
        ExamExamData testExamExamData = examExamDataList.get(examExamDataList.size() - 1);
        assertThat(testExamExamData.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testExamExamData.getEndTime()).isEqualTo(UPDATED_END_TIME);
        assertThat(testExamExamData.getScore()).isEqualTo(UPDATED_SCORE);
        assertThat(testExamExamData.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testExamExamData.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testExamExamData.getCheck()).isEqualTo(UPDATED_CHECK);
    }

    @Test
    @Transactional
    public void updateNonExistingExamExamData() throws Exception {
        int databaseSizeBeforeUpdate = examExamDataRepository.findAll().size();

        // Create the ExamExamData
        ExamExamDataDTO examExamDataDTO = examExamDataMapper.toDto(examExamData);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restExamExamDataMockMvc.perform(put("/api/exam-exam-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(examExamDataDTO)))
            .andExpect(status().isCreated());

        // Validate the ExamExamData in the database
        List<ExamExamData> examExamDataList = examExamDataRepository.findAll();
        assertThat(examExamDataList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteExamExamData() throws Exception {
        // Initialize the database
        examExamDataRepository.saveAndFlush(examExamData);
        int databaseSizeBeforeDelete = examExamDataRepository.findAll().size();

        // Get the examExamData
        restExamExamDataMockMvc.perform(delete("/api/exam-exam-data/{id}", examExamData.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ExamExamData> examExamDataList = examExamDataRepository.findAll();
        assertThat(examExamDataList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExamExamData.class);
        ExamExamData examExamData1 = new ExamExamData();
        examExamData1.setId(1L);
        ExamExamData examExamData2 = new ExamExamData();
        examExamData2.setId(examExamData1.getId());
        assertThat(examExamData1).isEqualTo(examExamData2);
        examExamData2.setId(2L);
        assertThat(examExamData1).isNotEqualTo(examExamData2);
        examExamData1.setId(null);
        assertThat(examExamData1).isNotEqualTo(examExamData2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExamExamDataDTO.class);
        ExamExamDataDTO examExamDataDTO1 = new ExamExamDataDTO();
        examExamDataDTO1.setId(1L);
        ExamExamDataDTO examExamDataDTO2 = new ExamExamDataDTO();
        assertThat(examExamDataDTO1).isNotEqualTo(examExamDataDTO2);
        examExamDataDTO2.setId(examExamDataDTO1.getId());
        assertThat(examExamDataDTO1).isEqualTo(examExamDataDTO2);
        examExamDataDTO2.setId(2L);
        assertThat(examExamDataDTO1).isNotEqualTo(examExamDataDTO2);
        examExamDataDTO1.setId(null);
        assertThat(examExamDataDTO1).isNotEqualTo(examExamDataDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(examExamDataMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(examExamDataMapper.fromId(null)).isNull();
    }
}
