package cn.ctodb.msp.exam.web.rest;

import cn.ctodb.msp.exam.ExamApp;

import cn.ctodb.msp.exam.config.SecurityBeanOverrideConfiguration;

import cn.ctodb.msp.exam.domain.ExamPager;
import cn.ctodb.msp.exam.repository.ExamPagerRepository;
import cn.ctodb.msp.exam.service.ExamPagerService;
import cn.ctodb.msp.exam.service.dto.ExamPagerDTO;
import cn.ctodb.msp.exam.service.mapper.ExamPagerMapper;
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

/**
 * Test class for the ExamPagerResource REST controller.
 *
 * @see ExamPagerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ExamApp.class, SecurityBeanOverrideConfiguration.class})
public class ExamPagerResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_START_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_START_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_END_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_END_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_SHOW_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_SHOW_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Integer DEFAULT_TOTAL_SCORE = 1;
    private static final Integer UPDATED_TOTAL_SCORE = 2;

    private static final Integer DEFAULT_COUNT = 1;
    private static final Integer UPDATED_COUNT = 2;

    private static final Integer DEFAULT_DURATION = 1;
    private static final Integer UPDATED_DURATION = 2;

    private static final Integer DEFAULT_USER_TIME = 1;
    private static final Integer UPDATED_USER_TIME = 2;

    private static final String DEFAULT_DATA = "AAAAAAAAAA";
    private static final String UPDATED_DATA = "BBBBBBBBBB";

    @Autowired
    private ExamPagerRepository examPagerRepository;

    @Autowired
    private ExamPagerMapper examPagerMapper;

    @Autowired
    private ExamPagerService examPagerService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restExamPagerMockMvc;

    private ExamPager examPager;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ExamPagerResource examPagerResource = new ExamPagerResource(examPagerService);
        this.restExamPagerMockMvc = MockMvcBuilders.standaloneSetup(examPagerResource)
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
    public static ExamPager createEntity(EntityManager em) {
        ExamPager examPager = new ExamPager()
            .name(DEFAULT_NAME)
            .startTime(DEFAULT_START_TIME)
            .endTime(DEFAULT_END_TIME)
            .showTime(DEFAULT_SHOW_TIME)
            .totalScore(DEFAULT_TOTAL_SCORE)
            .count(DEFAULT_COUNT)
            .duration(DEFAULT_DURATION)
            .userTime(DEFAULT_USER_TIME)
            .data(DEFAULT_DATA);
        return examPager;
    }

    @Before
    public void initTest() {
        examPager = createEntity(em);
    }

    @Test
    @Transactional
    public void createExamPager() throws Exception {
        int databaseSizeBeforeCreate = examPagerRepository.findAll().size();

        // Create the ExamPager
        ExamPagerDTO examPagerDTO = examPagerMapper.toDto(examPager);
        restExamPagerMockMvc.perform(post("/api/exam-pagers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(examPagerDTO)))
            .andExpect(status().isCreated());

        // Validate the ExamPager in the database
        List<ExamPager> examPagerList = examPagerRepository.findAll();
        assertThat(examPagerList).hasSize(databaseSizeBeforeCreate + 1);
        ExamPager testExamPager = examPagerList.get(examPagerList.size() - 1);
        assertThat(testExamPager.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testExamPager.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testExamPager.getEndTime()).isEqualTo(DEFAULT_END_TIME);
        assertThat(testExamPager.getShowTime()).isEqualTo(DEFAULT_SHOW_TIME);
        assertThat(testExamPager.getTotalScore()).isEqualTo(DEFAULT_TOTAL_SCORE);
        assertThat(testExamPager.getCount()).isEqualTo(DEFAULT_COUNT);
        assertThat(testExamPager.getDuration()).isEqualTo(DEFAULT_DURATION);
        assertThat(testExamPager.getUserTime()).isEqualTo(DEFAULT_USER_TIME);
        assertThat(testExamPager.getData()).isEqualTo(DEFAULT_DATA);
    }

    @Test
    @Transactional
    public void createExamPagerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = examPagerRepository.findAll().size();

        // Create the ExamPager with an existing ID
        examPager.setId(1L);
        ExamPagerDTO examPagerDTO = examPagerMapper.toDto(examPager);

        // An entity with an existing ID cannot be created, so this API call must fail
        restExamPagerMockMvc.perform(post("/api/exam-pagers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(examPagerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ExamPager in the database
        List<ExamPager> examPagerList = examPagerRepository.findAll();
        assertThat(examPagerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllExamPagers() throws Exception {
        // Initialize the database
        examPagerRepository.saveAndFlush(examPager);

        // Get all the examPagerList
        restExamPagerMockMvc.perform(get("/api/exam-pagers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(examPager.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(sameInstant(DEFAULT_START_TIME))))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(sameInstant(DEFAULT_END_TIME))))
            .andExpect(jsonPath("$.[*].showTime").value(hasItem(sameInstant(DEFAULT_SHOW_TIME))))
            .andExpect(jsonPath("$.[*].totalScore").value(hasItem(DEFAULT_TOTAL_SCORE)))
            .andExpect(jsonPath("$.[*].count").value(hasItem(DEFAULT_COUNT)))
            .andExpect(jsonPath("$.[*].duration").value(hasItem(DEFAULT_DURATION)))
            .andExpect(jsonPath("$.[*].userTime").value(hasItem(DEFAULT_USER_TIME)))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA.toString())));
    }

    @Test
    @Transactional
    public void getExamPager() throws Exception {
        // Initialize the database
        examPagerRepository.saveAndFlush(examPager);

        // Get the examPager
        restExamPagerMockMvc.perform(get("/api/exam-pagers/{id}", examPager.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(examPager.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.startTime").value(sameInstant(DEFAULT_START_TIME)))
            .andExpect(jsonPath("$.endTime").value(sameInstant(DEFAULT_END_TIME)))
            .andExpect(jsonPath("$.showTime").value(sameInstant(DEFAULT_SHOW_TIME)))
            .andExpect(jsonPath("$.totalScore").value(DEFAULT_TOTAL_SCORE))
            .andExpect(jsonPath("$.count").value(DEFAULT_COUNT))
            .andExpect(jsonPath("$.duration").value(DEFAULT_DURATION))
            .andExpect(jsonPath("$.userTime").value(DEFAULT_USER_TIME))
            .andExpect(jsonPath("$.data").value(DEFAULT_DATA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingExamPager() throws Exception {
        // Get the examPager
        restExamPagerMockMvc.perform(get("/api/exam-pagers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExamPager() throws Exception {
        // Initialize the database
        examPagerRepository.saveAndFlush(examPager);
        int databaseSizeBeforeUpdate = examPagerRepository.findAll().size();

        // Update the examPager
        ExamPager updatedExamPager = examPagerRepository.findOne(examPager.getId());
        // Disconnect from session so that the updates on updatedExamPager are not directly saved in db
        em.detach(updatedExamPager);
        updatedExamPager
            .name(UPDATED_NAME)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME)
            .showTime(UPDATED_SHOW_TIME)
            .totalScore(UPDATED_TOTAL_SCORE)
            .count(UPDATED_COUNT)
            .duration(UPDATED_DURATION)
            .userTime(UPDATED_USER_TIME)
            .data(UPDATED_DATA);
        ExamPagerDTO examPagerDTO = examPagerMapper.toDto(updatedExamPager);

        restExamPagerMockMvc.perform(put("/api/exam-pagers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(examPagerDTO)))
            .andExpect(status().isOk());

        // Validate the ExamPager in the database
        List<ExamPager> examPagerList = examPagerRepository.findAll();
        assertThat(examPagerList).hasSize(databaseSizeBeforeUpdate);
        ExamPager testExamPager = examPagerList.get(examPagerList.size() - 1);
        assertThat(testExamPager.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testExamPager.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testExamPager.getEndTime()).isEqualTo(UPDATED_END_TIME);
        assertThat(testExamPager.getShowTime()).isEqualTo(UPDATED_SHOW_TIME);
        assertThat(testExamPager.getTotalScore()).isEqualTo(UPDATED_TOTAL_SCORE);
        assertThat(testExamPager.getCount()).isEqualTo(UPDATED_COUNT);
        assertThat(testExamPager.getDuration()).isEqualTo(UPDATED_DURATION);
        assertThat(testExamPager.getUserTime()).isEqualTo(UPDATED_USER_TIME);
        assertThat(testExamPager.getData()).isEqualTo(UPDATED_DATA);
    }

    @Test
    @Transactional
    public void updateNonExistingExamPager() throws Exception {
        int databaseSizeBeforeUpdate = examPagerRepository.findAll().size();

        // Create the ExamPager
        ExamPagerDTO examPagerDTO = examPagerMapper.toDto(examPager);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restExamPagerMockMvc.perform(put("/api/exam-pagers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(examPagerDTO)))
            .andExpect(status().isCreated());

        // Validate the ExamPager in the database
        List<ExamPager> examPagerList = examPagerRepository.findAll();
        assertThat(examPagerList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteExamPager() throws Exception {
        // Initialize the database
        examPagerRepository.saveAndFlush(examPager);
        int databaseSizeBeforeDelete = examPagerRepository.findAll().size();

        // Get the examPager
        restExamPagerMockMvc.perform(delete("/api/exam-pagers/{id}", examPager.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ExamPager> examPagerList = examPagerRepository.findAll();
        assertThat(examPagerList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExamPager.class);
        ExamPager examPager1 = new ExamPager();
        examPager1.setId(1L);
        ExamPager examPager2 = new ExamPager();
        examPager2.setId(examPager1.getId());
        assertThat(examPager1).isEqualTo(examPager2);
        examPager2.setId(2L);
        assertThat(examPager1).isNotEqualTo(examPager2);
        examPager1.setId(null);
        assertThat(examPager1).isNotEqualTo(examPager2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExamPagerDTO.class);
        ExamPagerDTO examPagerDTO1 = new ExamPagerDTO();
        examPagerDTO1.setId(1L);
        ExamPagerDTO examPagerDTO2 = new ExamPagerDTO();
        assertThat(examPagerDTO1).isNotEqualTo(examPagerDTO2);
        examPagerDTO2.setId(examPagerDTO1.getId());
        assertThat(examPagerDTO1).isEqualTo(examPagerDTO2);
        examPagerDTO2.setId(2L);
        assertThat(examPagerDTO1).isNotEqualTo(examPagerDTO2);
        examPagerDTO1.setId(null);
        assertThat(examPagerDTO1).isNotEqualTo(examPagerDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(examPagerMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(examPagerMapper.fromId(null)).isNull();
    }
}
