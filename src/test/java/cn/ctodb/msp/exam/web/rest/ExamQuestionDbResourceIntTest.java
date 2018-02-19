package cn.ctodb.msp.exam.web.rest;

import cn.ctodb.msp.exam.ExamApp;

import cn.ctodb.msp.exam.config.SecurityBeanOverrideConfiguration;

import cn.ctodb.msp.exam.domain.ExamQuestionDb;
import cn.ctodb.msp.exam.repository.ExamQuestionDbRepository;
import cn.ctodb.msp.exam.service.ExamQuestionDbService;
import cn.ctodb.msp.exam.service.dto.ExamQuestionDbDTO;
import cn.ctodb.msp.exam.service.mapper.ExamQuestionDbMapper;
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

import javax.persistence.EntityManager;
import java.util.List;

import static cn.ctodb.msp.exam.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import cn.ctodb.msp.exam.domain.enumeration.Status;
/**
 * Test class for the ExamQuestionDbResource REST controller.
 *
 * @see ExamQuestionDbResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ExamApp.class, SecurityBeanOverrideConfiguration.class})
public class ExamQuestionDbResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LOGO = "AAAAAAAAAA";
    private static final String UPDATED_LOGO = "BBBBBBBBBB";

    private static final Status DEFAULT_STATUS = Status.ENABLE;
    private static final Status UPDATED_STATUS = Status.DISABLE;

    private static final String DEFAULT_REMARK = "AAAAAAAAAA";
    private static final String UPDATED_REMARK = "BBBBBBBBBB";

    @Autowired
    private ExamQuestionDbRepository examQuestionDbRepository;

    @Autowired
    private ExamQuestionDbMapper examQuestionDbMapper;

    @Autowired
    private ExamQuestionDbService examQuestionDbService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restExamQuestionDbMockMvc;

    private ExamQuestionDb examQuestionDb;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ExamQuestionDbResource examQuestionDbResource = new ExamQuestionDbResource(examQuestionDbService);
        this.restExamQuestionDbMockMvc = MockMvcBuilders.standaloneSetup(examQuestionDbResource)
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
    public static ExamQuestionDb createEntity(EntityManager em) {
        ExamQuestionDb examQuestionDb = new ExamQuestionDb()
            .name(DEFAULT_NAME)
            .logo(DEFAULT_LOGO)
            .status(DEFAULT_STATUS)
            .remark(DEFAULT_REMARK);
        return examQuestionDb;
    }

    @Before
    public void initTest() {
        examQuestionDb = createEntity(em);
    }

    @Test
    @Transactional
    public void createExamQuestionDb() throws Exception {
        int databaseSizeBeforeCreate = examQuestionDbRepository.findAll().size();

        // Create the ExamQuestionDb
        ExamQuestionDbDTO examQuestionDbDTO = examQuestionDbMapper.toDto(examQuestionDb);
        restExamQuestionDbMockMvc.perform(post("/api/exam-question-dbs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(examQuestionDbDTO)))
            .andExpect(status().isCreated());

        // Validate the ExamQuestionDb in the database
        List<ExamQuestionDb> examQuestionDbList = examQuestionDbRepository.findAll();
        assertThat(examQuestionDbList).hasSize(databaseSizeBeforeCreate + 1);
        ExamQuestionDb testExamQuestionDb = examQuestionDbList.get(examQuestionDbList.size() - 1);
        assertThat(testExamQuestionDb.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testExamQuestionDb.getLogo()).isEqualTo(DEFAULT_LOGO);
        assertThat(testExamQuestionDb.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testExamQuestionDb.getRemark()).isEqualTo(DEFAULT_REMARK);
    }

    @Test
    @Transactional
    public void createExamQuestionDbWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = examQuestionDbRepository.findAll().size();

        // Create the ExamQuestionDb with an existing ID
        examQuestionDb.setId(1L);
        ExamQuestionDbDTO examQuestionDbDTO = examQuestionDbMapper.toDto(examQuestionDb);

        // An entity with an existing ID cannot be created, so this API call must fail
        restExamQuestionDbMockMvc.perform(post("/api/exam-question-dbs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(examQuestionDbDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ExamQuestionDb in the database
        List<ExamQuestionDb> examQuestionDbList = examQuestionDbRepository.findAll();
        assertThat(examQuestionDbList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllExamQuestionDbs() throws Exception {
        // Initialize the database
        examQuestionDbRepository.saveAndFlush(examQuestionDb);

        // Get all the examQuestionDbList
        restExamQuestionDbMockMvc.perform(get("/api/exam-question-dbs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(examQuestionDb.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].logo").value(hasItem(DEFAULT_LOGO.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK.toString())));
    }

    @Test
    @Transactional
    public void getExamQuestionDb() throws Exception {
        // Initialize the database
        examQuestionDbRepository.saveAndFlush(examQuestionDb);

        // Get the examQuestionDb
        restExamQuestionDbMockMvc.perform(get("/api/exam-question-dbs/{id}", examQuestionDb.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(examQuestionDb.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.logo").value(DEFAULT_LOGO.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.remark").value(DEFAULT_REMARK.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingExamQuestionDb() throws Exception {
        // Get the examQuestionDb
        restExamQuestionDbMockMvc.perform(get("/api/exam-question-dbs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExamQuestionDb() throws Exception {
        // Initialize the database
        examQuestionDbRepository.saveAndFlush(examQuestionDb);
        int databaseSizeBeforeUpdate = examQuestionDbRepository.findAll().size();

        // Update the examQuestionDb
        ExamQuestionDb updatedExamQuestionDb = examQuestionDbRepository.findOne(examQuestionDb.getId());
        // Disconnect from session so that the updates on updatedExamQuestionDb are not directly saved in db
        em.detach(updatedExamQuestionDb);
        updatedExamQuestionDb
            .name(UPDATED_NAME)
            .logo(UPDATED_LOGO)
            .status(UPDATED_STATUS)
            .remark(UPDATED_REMARK);
        ExamQuestionDbDTO examQuestionDbDTO = examQuestionDbMapper.toDto(updatedExamQuestionDb);

        restExamQuestionDbMockMvc.perform(put("/api/exam-question-dbs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(examQuestionDbDTO)))
            .andExpect(status().isOk());

        // Validate the ExamQuestionDb in the database
        List<ExamQuestionDb> examQuestionDbList = examQuestionDbRepository.findAll();
        assertThat(examQuestionDbList).hasSize(databaseSizeBeforeUpdate);
        ExamQuestionDb testExamQuestionDb = examQuestionDbList.get(examQuestionDbList.size() - 1);
        assertThat(testExamQuestionDb.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testExamQuestionDb.getLogo()).isEqualTo(UPDATED_LOGO);
        assertThat(testExamQuestionDb.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testExamQuestionDb.getRemark()).isEqualTo(UPDATED_REMARK);
    }

    @Test
    @Transactional
    public void updateNonExistingExamQuestionDb() throws Exception {
        int databaseSizeBeforeUpdate = examQuestionDbRepository.findAll().size();

        // Create the ExamQuestionDb
        ExamQuestionDbDTO examQuestionDbDTO = examQuestionDbMapper.toDto(examQuestionDb);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restExamQuestionDbMockMvc.perform(put("/api/exam-question-dbs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(examQuestionDbDTO)))
            .andExpect(status().isCreated());

        // Validate the ExamQuestionDb in the database
        List<ExamQuestionDb> examQuestionDbList = examQuestionDbRepository.findAll();
        assertThat(examQuestionDbList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteExamQuestionDb() throws Exception {
        // Initialize the database
        examQuestionDbRepository.saveAndFlush(examQuestionDb);
        int databaseSizeBeforeDelete = examQuestionDbRepository.findAll().size();

        // Get the examQuestionDb
        restExamQuestionDbMockMvc.perform(delete("/api/exam-question-dbs/{id}", examQuestionDb.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ExamQuestionDb> examQuestionDbList = examQuestionDbRepository.findAll();
        assertThat(examQuestionDbList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExamQuestionDb.class);
        ExamQuestionDb examQuestionDb1 = new ExamQuestionDb();
        examQuestionDb1.setId(1L);
        ExamQuestionDb examQuestionDb2 = new ExamQuestionDb();
        examQuestionDb2.setId(examQuestionDb1.getId());
        assertThat(examQuestionDb1).isEqualTo(examQuestionDb2);
        examQuestionDb2.setId(2L);
        assertThat(examQuestionDb1).isNotEqualTo(examQuestionDb2);
        examQuestionDb1.setId(null);
        assertThat(examQuestionDb1).isNotEqualTo(examQuestionDb2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExamQuestionDbDTO.class);
        ExamQuestionDbDTO examQuestionDbDTO1 = new ExamQuestionDbDTO();
        examQuestionDbDTO1.setId(1L);
        ExamQuestionDbDTO examQuestionDbDTO2 = new ExamQuestionDbDTO();
        assertThat(examQuestionDbDTO1).isNotEqualTo(examQuestionDbDTO2);
        examQuestionDbDTO2.setId(examQuestionDbDTO1.getId());
        assertThat(examQuestionDbDTO1).isEqualTo(examQuestionDbDTO2);
        examQuestionDbDTO2.setId(2L);
        assertThat(examQuestionDbDTO1).isNotEqualTo(examQuestionDbDTO2);
        examQuestionDbDTO1.setId(null);
        assertThat(examQuestionDbDTO1).isNotEqualTo(examQuestionDbDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(examQuestionDbMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(examQuestionDbMapper.fromId(null)).isNull();
    }
}
