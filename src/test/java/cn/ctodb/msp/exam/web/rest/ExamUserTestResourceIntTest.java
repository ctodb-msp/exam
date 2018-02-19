package cn.ctodb.msp.exam.web.rest;

import cn.ctodb.msp.exam.ExamApp;

import cn.ctodb.msp.exam.config.SecurityBeanOverrideConfiguration;

import cn.ctodb.msp.exam.domain.ExamUserTest;
import cn.ctodb.msp.exam.repository.ExamUserTestRepository;
import cn.ctodb.msp.exam.service.ExamUserTestService;
import cn.ctodb.msp.exam.service.dto.ExamUserTestDTO;
import cn.ctodb.msp.exam.service.mapper.ExamUserTestMapper;
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
 * Test class for the ExamUserTestResource REST controller.
 *
 * @see ExamUserTestResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ExamApp.class, SecurityBeanOverrideConfiguration.class})
public class ExamUserTestResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_DURATION = 1;
    private static final Integer UPDATED_DURATION = 2;

    private static final Integer DEFAULT_TOTAL_SCORE = 1;
    private static final Integer UPDATED_TOTAL_SCORE = 2;

    private static final Integer DEFAULT_USER_SCORE = 1;
    private static final Integer UPDATED_USER_SCORE = 2;

    private static final String DEFAULT_PAPER = "AAAAAAAAAA";
    private static final String UPDATED_PAPER = "BBBBBBBBBB";

    private static final String DEFAULT_ANSWER = "AAAAAAAAAA";
    private static final String UPDATED_ANSWER = "BBBBBBBBBB";

    private static final String DEFAULT_CHECK = "AAAAAAAAAA";
    private static final String UPDATED_CHECK = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_TESTDATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_TESTDATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private ExamUserTestRepository examUserTestRepository;

    @Autowired
    private ExamUserTestMapper examUserTestMapper;

    @Autowired
    private ExamUserTestService examUserTestService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restExamUserTestMockMvc;

    private ExamUserTest examUserTest;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ExamUserTestResource examUserTestResource = new ExamUserTestResource(examUserTestService);
        this.restExamUserTestMockMvc = MockMvcBuilders.standaloneSetup(examUserTestResource)
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
    public static ExamUserTest createEntity(EntityManager em) {
        ExamUserTest examUserTest = new ExamUserTest()
            .name(DEFAULT_NAME)
            .duration(DEFAULT_DURATION)
            .totalScore(DEFAULT_TOTAL_SCORE)
            .userScore(DEFAULT_USER_SCORE)
            .paper(DEFAULT_PAPER)
            .answer(DEFAULT_ANSWER)
            .check(DEFAULT_CHECK)
            .testdate(DEFAULT_TESTDATE);
        return examUserTest;
    }

    @Before
    public void initTest() {
        examUserTest = createEntity(em);
    }

    @Test
    @Transactional
    public void createExamUserTest() throws Exception {
        int databaseSizeBeforeCreate = examUserTestRepository.findAll().size();

        // Create the ExamUserTest
        ExamUserTestDTO examUserTestDTO = examUserTestMapper.toDto(examUserTest);
        restExamUserTestMockMvc.perform(post("/api/exam-user-tests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(examUserTestDTO)))
            .andExpect(status().isCreated());

        // Validate the ExamUserTest in the database
        List<ExamUserTest> examUserTestList = examUserTestRepository.findAll();
        assertThat(examUserTestList).hasSize(databaseSizeBeforeCreate + 1);
        ExamUserTest testExamUserTest = examUserTestList.get(examUserTestList.size() - 1);
        assertThat(testExamUserTest.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testExamUserTest.getDuration()).isEqualTo(DEFAULT_DURATION);
        assertThat(testExamUserTest.getTotalScore()).isEqualTo(DEFAULT_TOTAL_SCORE);
        assertThat(testExamUserTest.getUserScore()).isEqualTo(DEFAULT_USER_SCORE);
        assertThat(testExamUserTest.getPaper()).isEqualTo(DEFAULT_PAPER);
        assertThat(testExamUserTest.getAnswer()).isEqualTo(DEFAULT_ANSWER);
        assertThat(testExamUserTest.getCheck()).isEqualTo(DEFAULT_CHECK);
        assertThat(testExamUserTest.getTestdate()).isEqualTo(DEFAULT_TESTDATE);
    }

    @Test
    @Transactional
    public void createExamUserTestWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = examUserTestRepository.findAll().size();

        // Create the ExamUserTest with an existing ID
        examUserTest.setId(1L);
        ExamUserTestDTO examUserTestDTO = examUserTestMapper.toDto(examUserTest);

        // An entity with an existing ID cannot be created, so this API call must fail
        restExamUserTestMockMvc.perform(post("/api/exam-user-tests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(examUserTestDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ExamUserTest in the database
        List<ExamUserTest> examUserTestList = examUserTestRepository.findAll();
        assertThat(examUserTestList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllExamUserTests() throws Exception {
        // Initialize the database
        examUserTestRepository.saveAndFlush(examUserTest);

        // Get all the examUserTestList
        restExamUserTestMockMvc.perform(get("/api/exam-user-tests?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(examUserTest.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].duration").value(hasItem(DEFAULT_DURATION)))
            .andExpect(jsonPath("$.[*].totalScore").value(hasItem(DEFAULT_TOTAL_SCORE)))
            .andExpect(jsonPath("$.[*].userScore").value(hasItem(DEFAULT_USER_SCORE)))
            .andExpect(jsonPath("$.[*].paper").value(hasItem(DEFAULT_PAPER.toString())))
            .andExpect(jsonPath("$.[*].answer").value(hasItem(DEFAULT_ANSWER.toString())))
            .andExpect(jsonPath("$.[*].check").value(hasItem(DEFAULT_CHECK.toString())))
            .andExpect(jsonPath("$.[*].testdate").value(hasItem(sameInstant(DEFAULT_TESTDATE))));
    }

    @Test
    @Transactional
    public void getExamUserTest() throws Exception {
        // Initialize the database
        examUserTestRepository.saveAndFlush(examUserTest);

        // Get the examUserTest
        restExamUserTestMockMvc.perform(get("/api/exam-user-tests/{id}", examUserTest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(examUserTest.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.duration").value(DEFAULT_DURATION))
            .andExpect(jsonPath("$.totalScore").value(DEFAULT_TOTAL_SCORE))
            .andExpect(jsonPath("$.userScore").value(DEFAULT_USER_SCORE))
            .andExpect(jsonPath("$.paper").value(DEFAULT_PAPER.toString()))
            .andExpect(jsonPath("$.answer").value(DEFAULT_ANSWER.toString()))
            .andExpect(jsonPath("$.check").value(DEFAULT_CHECK.toString()))
            .andExpect(jsonPath("$.testdate").value(sameInstant(DEFAULT_TESTDATE)));
    }

    @Test
    @Transactional
    public void getNonExistingExamUserTest() throws Exception {
        // Get the examUserTest
        restExamUserTestMockMvc.perform(get("/api/exam-user-tests/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExamUserTest() throws Exception {
        // Initialize the database
        examUserTestRepository.saveAndFlush(examUserTest);
        int databaseSizeBeforeUpdate = examUserTestRepository.findAll().size();

        // Update the examUserTest
        ExamUserTest updatedExamUserTest = examUserTestRepository.findOne(examUserTest.getId());
        // Disconnect from session so that the updates on updatedExamUserTest are not directly saved in db
        em.detach(updatedExamUserTest);
        updatedExamUserTest
            .name(UPDATED_NAME)
            .duration(UPDATED_DURATION)
            .totalScore(UPDATED_TOTAL_SCORE)
            .userScore(UPDATED_USER_SCORE)
            .paper(UPDATED_PAPER)
            .answer(UPDATED_ANSWER)
            .check(UPDATED_CHECK)
            .testdate(UPDATED_TESTDATE);
        ExamUserTestDTO examUserTestDTO = examUserTestMapper.toDto(updatedExamUserTest);

        restExamUserTestMockMvc.perform(put("/api/exam-user-tests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(examUserTestDTO)))
            .andExpect(status().isOk());

        // Validate the ExamUserTest in the database
        List<ExamUserTest> examUserTestList = examUserTestRepository.findAll();
        assertThat(examUserTestList).hasSize(databaseSizeBeforeUpdate);
        ExamUserTest testExamUserTest = examUserTestList.get(examUserTestList.size() - 1);
        assertThat(testExamUserTest.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testExamUserTest.getDuration()).isEqualTo(UPDATED_DURATION);
        assertThat(testExamUserTest.getTotalScore()).isEqualTo(UPDATED_TOTAL_SCORE);
        assertThat(testExamUserTest.getUserScore()).isEqualTo(UPDATED_USER_SCORE);
        assertThat(testExamUserTest.getPaper()).isEqualTo(UPDATED_PAPER);
        assertThat(testExamUserTest.getAnswer()).isEqualTo(UPDATED_ANSWER);
        assertThat(testExamUserTest.getCheck()).isEqualTo(UPDATED_CHECK);
        assertThat(testExamUserTest.getTestdate()).isEqualTo(UPDATED_TESTDATE);
    }

    @Test
    @Transactional
    public void updateNonExistingExamUserTest() throws Exception {
        int databaseSizeBeforeUpdate = examUserTestRepository.findAll().size();

        // Create the ExamUserTest
        ExamUserTestDTO examUserTestDTO = examUserTestMapper.toDto(examUserTest);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restExamUserTestMockMvc.perform(put("/api/exam-user-tests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(examUserTestDTO)))
            .andExpect(status().isCreated());

        // Validate the ExamUserTest in the database
        List<ExamUserTest> examUserTestList = examUserTestRepository.findAll();
        assertThat(examUserTestList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteExamUserTest() throws Exception {
        // Initialize the database
        examUserTestRepository.saveAndFlush(examUserTest);
        int databaseSizeBeforeDelete = examUserTestRepository.findAll().size();

        // Get the examUserTest
        restExamUserTestMockMvc.perform(delete("/api/exam-user-tests/{id}", examUserTest.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ExamUserTest> examUserTestList = examUserTestRepository.findAll();
        assertThat(examUserTestList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExamUserTest.class);
        ExamUserTest examUserTest1 = new ExamUserTest();
        examUserTest1.setId(1L);
        ExamUserTest examUserTest2 = new ExamUserTest();
        examUserTest2.setId(examUserTest1.getId());
        assertThat(examUserTest1).isEqualTo(examUserTest2);
        examUserTest2.setId(2L);
        assertThat(examUserTest1).isNotEqualTo(examUserTest2);
        examUserTest1.setId(null);
        assertThat(examUserTest1).isNotEqualTo(examUserTest2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExamUserTestDTO.class);
        ExamUserTestDTO examUserTestDTO1 = new ExamUserTestDTO();
        examUserTestDTO1.setId(1L);
        ExamUserTestDTO examUserTestDTO2 = new ExamUserTestDTO();
        assertThat(examUserTestDTO1).isNotEqualTo(examUserTestDTO2);
        examUserTestDTO2.setId(examUserTestDTO1.getId());
        assertThat(examUserTestDTO1).isEqualTo(examUserTestDTO2);
        examUserTestDTO2.setId(2L);
        assertThat(examUserTestDTO1).isNotEqualTo(examUserTestDTO2);
        examUserTestDTO1.setId(null);
        assertThat(examUserTestDTO1).isNotEqualTo(examUserTestDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(examUserTestMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(examUserTestMapper.fromId(null)).isNull();
    }
}
