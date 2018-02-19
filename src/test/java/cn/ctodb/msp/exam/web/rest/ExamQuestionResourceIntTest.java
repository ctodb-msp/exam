package cn.ctodb.msp.exam.web.rest;

import cn.ctodb.msp.exam.ExamApp;

import cn.ctodb.msp.exam.config.SecurityBeanOverrideConfiguration;

import cn.ctodb.msp.exam.domain.ExamQuestion;
import cn.ctodb.msp.exam.repository.ExamQuestionRepository;
import cn.ctodb.msp.exam.service.ExamQuestionService;
import cn.ctodb.msp.exam.service.dto.ExamQuestionDTO;
import cn.ctodb.msp.exam.service.mapper.ExamQuestionMapper;
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
import java.util.List;

import static cn.ctodb.msp.exam.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import cn.ctodb.msp.exam.domain.enumeration.QuestionType;
import cn.ctodb.msp.exam.domain.enumeration.Status;
/**
 * Test class for the ExamQuestionResource REST controller.
 *
 * @see ExamQuestionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ExamApp.class, SecurityBeanOverrideConfiguration.class})
public class ExamQuestionResourceIntTest {

    private static final QuestionType DEFAULT_TYPE = QuestionType.SIMPLE;
    private static final QuestionType UPDATED_TYPE = QuestionType.COMBOX;

    private static final String DEFAULT_QUESTION = "AAAAAAAAAA";
    private static final String UPDATED_QUESTION = "BBBBBBBBBB";

    private static final Float DEFAULT_SCORE = 1F;
    private static final Float UPDATED_SCORE = 2F;

    private static final Status DEFAULT_STATUS = Status.ENABLE;
    private static final Status UPDATED_STATUS = Status.DISABLE;

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final String DEFAULT_KEY = "AAAAAAAAAA";
    private static final String UPDATED_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_RESOLVE = "AAAAAAAAAA";
    private static final String UPDATED_RESOLVE = "BBBBBBBBBB";

    private static final String DEFAULT_DATA = "AAAAAAAAAA";
    private static final String UPDATED_DATA = "BBBBBBBBBB";

    private static final Integer DEFAULT_LEVEL = 1;
    private static final Integer UPDATED_LEVEL = 2;

    @Autowired
    private ExamQuestionRepository examQuestionRepository;

    @Autowired
    private ExamQuestionMapper examQuestionMapper;

    @Autowired
    private ExamQuestionService examQuestionService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restExamQuestionMockMvc;

    private ExamQuestion examQuestion;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ExamQuestionResource examQuestionResource = new ExamQuestionResource(examQuestionService);
        this.restExamQuestionMockMvc = MockMvcBuilders.standaloneSetup(examQuestionResource)
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
    public static ExamQuestion createEntity(EntityManager em) {
        ExamQuestion examQuestion = new ExamQuestion()
            .type(DEFAULT_TYPE)
            .question(DEFAULT_QUESTION)
            .score(DEFAULT_SCORE)
            .status(DEFAULT_STATUS)
            .content(DEFAULT_CONTENT)
            .key(DEFAULT_KEY)
            .resolve(DEFAULT_RESOLVE)
            .data(DEFAULT_DATA)
            .level(DEFAULT_LEVEL);
        return examQuestion;
    }

    @Before
    public void initTest() {
        examQuestion = createEntity(em);
    }

    @Test
    @Transactional
    public void createExamQuestion() throws Exception {
        int databaseSizeBeforeCreate = examQuestionRepository.findAll().size();

        // Create the ExamQuestion
        ExamQuestionDTO examQuestionDTO = examQuestionMapper.toDto(examQuestion);
        restExamQuestionMockMvc.perform(post("/api/exam-questions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(examQuestionDTO)))
            .andExpect(status().isCreated());

        // Validate the ExamQuestion in the database
        List<ExamQuestion> examQuestionList = examQuestionRepository.findAll();
        assertThat(examQuestionList).hasSize(databaseSizeBeforeCreate + 1);
        ExamQuestion testExamQuestion = examQuestionList.get(examQuestionList.size() - 1);
        assertThat(testExamQuestion.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testExamQuestion.getQuestion()).isEqualTo(DEFAULT_QUESTION);
        assertThat(testExamQuestion.getScore()).isEqualTo(DEFAULT_SCORE);
        assertThat(testExamQuestion.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testExamQuestion.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testExamQuestion.getKey()).isEqualTo(DEFAULT_KEY);
        assertThat(testExamQuestion.getResolve()).isEqualTo(DEFAULT_RESOLVE);
        assertThat(testExamQuestion.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testExamQuestion.getLevel()).isEqualTo(DEFAULT_LEVEL);
    }

    @Test
    @Transactional
    public void createExamQuestionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = examQuestionRepository.findAll().size();

        // Create the ExamQuestion with an existing ID
        examQuestion.setId(1L);
        ExamQuestionDTO examQuestionDTO = examQuestionMapper.toDto(examQuestion);

        // An entity with an existing ID cannot be created, so this API call must fail
        restExamQuestionMockMvc.perform(post("/api/exam-questions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(examQuestionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ExamQuestion in the database
        List<ExamQuestion> examQuestionList = examQuestionRepository.findAll();
        assertThat(examQuestionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllExamQuestions() throws Exception {
        // Initialize the database
        examQuestionRepository.saveAndFlush(examQuestion);

        // Get all the examQuestionList
        restExamQuestionMockMvc.perform(get("/api/exam-questions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(examQuestion.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].question").value(hasItem(DEFAULT_QUESTION.toString())))
            .andExpect(jsonPath("$.[*].score").value(hasItem(DEFAULT_SCORE.doubleValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY.toString())))
            .andExpect(jsonPath("$.[*].resolve").value(hasItem(DEFAULT_RESOLVE.toString())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA.toString())))
            .andExpect(jsonPath("$.[*].level").value(hasItem(DEFAULT_LEVEL)));
    }

    @Test
    @Transactional
    public void getExamQuestion() throws Exception {
        // Initialize the database
        examQuestionRepository.saveAndFlush(examQuestion);

        // Get the examQuestion
        restExamQuestionMockMvc.perform(get("/api/exam-questions/{id}", examQuestion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(examQuestion.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.question").value(DEFAULT_QUESTION.toString()))
            .andExpect(jsonPath("$.score").value(DEFAULT_SCORE.doubleValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT.toString()))
            .andExpect(jsonPath("$.key").value(DEFAULT_KEY.toString()))
            .andExpect(jsonPath("$.resolve").value(DEFAULT_RESOLVE.toString()))
            .andExpect(jsonPath("$.data").value(DEFAULT_DATA.toString()))
            .andExpect(jsonPath("$.level").value(DEFAULT_LEVEL));
    }

    @Test
    @Transactional
    public void getNonExistingExamQuestion() throws Exception {
        // Get the examQuestion
        restExamQuestionMockMvc.perform(get("/api/exam-questions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExamQuestion() throws Exception {
        // Initialize the database
        examQuestionRepository.saveAndFlush(examQuestion);
        int databaseSizeBeforeUpdate = examQuestionRepository.findAll().size();

        // Update the examQuestion
        ExamQuestion updatedExamQuestion = examQuestionRepository.findOne(examQuestion.getId());
        // Disconnect from session so that the updates on updatedExamQuestion are not directly saved in db
        em.detach(updatedExamQuestion);
        updatedExamQuestion
            .type(UPDATED_TYPE)
            .question(UPDATED_QUESTION)
            .score(UPDATED_SCORE)
            .status(UPDATED_STATUS)
            .content(UPDATED_CONTENT)
            .key(UPDATED_KEY)
            .resolve(UPDATED_RESOLVE)
            .data(UPDATED_DATA)
            .level(UPDATED_LEVEL);
        ExamQuestionDTO examQuestionDTO = examQuestionMapper.toDto(updatedExamQuestion);

        restExamQuestionMockMvc.perform(put("/api/exam-questions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(examQuestionDTO)))
            .andExpect(status().isOk());

        // Validate the ExamQuestion in the database
        List<ExamQuestion> examQuestionList = examQuestionRepository.findAll();
        assertThat(examQuestionList).hasSize(databaseSizeBeforeUpdate);
        ExamQuestion testExamQuestion = examQuestionList.get(examQuestionList.size() - 1);
        assertThat(testExamQuestion.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testExamQuestion.getQuestion()).isEqualTo(UPDATED_QUESTION);
        assertThat(testExamQuestion.getScore()).isEqualTo(UPDATED_SCORE);
        assertThat(testExamQuestion.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testExamQuestion.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testExamQuestion.getKey()).isEqualTo(UPDATED_KEY);
        assertThat(testExamQuestion.getResolve()).isEqualTo(UPDATED_RESOLVE);
        assertThat(testExamQuestion.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testExamQuestion.getLevel()).isEqualTo(UPDATED_LEVEL);
    }

    @Test
    @Transactional
    public void updateNonExistingExamQuestion() throws Exception {
        int databaseSizeBeforeUpdate = examQuestionRepository.findAll().size();

        // Create the ExamQuestion
        ExamQuestionDTO examQuestionDTO = examQuestionMapper.toDto(examQuestion);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restExamQuestionMockMvc.perform(put("/api/exam-questions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(examQuestionDTO)))
            .andExpect(status().isCreated());

        // Validate the ExamQuestion in the database
        List<ExamQuestion> examQuestionList = examQuestionRepository.findAll();
        assertThat(examQuestionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteExamQuestion() throws Exception {
        // Initialize the database
        examQuestionRepository.saveAndFlush(examQuestion);
        int databaseSizeBeforeDelete = examQuestionRepository.findAll().size();

        // Get the examQuestion
        restExamQuestionMockMvc.perform(delete("/api/exam-questions/{id}", examQuestion.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ExamQuestion> examQuestionList = examQuestionRepository.findAll();
        assertThat(examQuestionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExamQuestion.class);
        ExamQuestion examQuestion1 = new ExamQuestion();
        examQuestion1.setId(1L);
        ExamQuestion examQuestion2 = new ExamQuestion();
        examQuestion2.setId(examQuestion1.getId());
        assertThat(examQuestion1).isEqualTo(examQuestion2);
        examQuestion2.setId(2L);
        assertThat(examQuestion1).isNotEqualTo(examQuestion2);
        examQuestion1.setId(null);
        assertThat(examQuestion1).isNotEqualTo(examQuestion2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExamQuestionDTO.class);
        ExamQuestionDTO examQuestionDTO1 = new ExamQuestionDTO();
        examQuestionDTO1.setId(1L);
        ExamQuestionDTO examQuestionDTO2 = new ExamQuestionDTO();
        assertThat(examQuestionDTO1).isNotEqualTo(examQuestionDTO2);
        examQuestionDTO2.setId(examQuestionDTO1.getId());
        assertThat(examQuestionDTO1).isEqualTo(examQuestionDTO2);
        examQuestionDTO2.setId(2L);
        assertThat(examQuestionDTO1).isNotEqualTo(examQuestionDTO2);
        examQuestionDTO1.setId(null);
        assertThat(examQuestionDTO1).isNotEqualTo(examQuestionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(examQuestionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(examQuestionMapper.fromId(null)).isNull();
    }
}
