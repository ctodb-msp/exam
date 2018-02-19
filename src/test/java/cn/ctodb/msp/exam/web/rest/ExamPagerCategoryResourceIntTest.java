package cn.ctodb.msp.exam.web.rest;

import cn.ctodb.msp.exam.ExamApp;

import cn.ctodb.msp.exam.config.SecurityBeanOverrideConfiguration;

import cn.ctodb.msp.exam.domain.ExamPagerCategory;
import cn.ctodb.msp.exam.repository.ExamPagerCategoryRepository;
import cn.ctodb.msp.exam.service.ExamPagerCategoryService;
import cn.ctodb.msp.exam.service.dto.ExamPagerCategoryDTO;
import cn.ctodb.msp.exam.service.mapper.ExamPagerCategoryMapper;
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
 * Test class for the ExamPagerCategoryResource REST controller.
 *
 * @see ExamPagerCategoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ExamApp.class, SecurityBeanOverrideConfiguration.class})
public class ExamPagerCategoryResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_REMARK = "AAAAAAAAAA";
    private static final String UPDATED_REMARK = "BBBBBBBBBB";

    private static final Status DEFAULT_STATUS = Status.ENABLE;
    private static final Status UPDATED_STATUS = Status.DISABLE;

    @Autowired
    private ExamPagerCategoryRepository examPagerCategoryRepository;

    @Autowired
    private ExamPagerCategoryMapper examPagerCategoryMapper;

    @Autowired
    private ExamPagerCategoryService examPagerCategoryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restExamPagerCategoryMockMvc;

    private ExamPagerCategory examPagerCategory;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ExamPagerCategoryResource examPagerCategoryResource = new ExamPagerCategoryResource(examPagerCategoryService);
        this.restExamPagerCategoryMockMvc = MockMvcBuilders.standaloneSetup(examPagerCategoryResource)
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
    public static ExamPagerCategory createEntity(EntityManager em) {
        ExamPagerCategory examPagerCategory = new ExamPagerCategory()
            .name(DEFAULT_NAME)
            .remark(DEFAULT_REMARK)
            .status(DEFAULT_STATUS);
        return examPagerCategory;
    }

    @Before
    public void initTest() {
        examPagerCategory = createEntity(em);
    }

    @Test
    @Transactional
    public void createExamPagerCategory() throws Exception {
        int databaseSizeBeforeCreate = examPagerCategoryRepository.findAll().size();

        // Create the ExamPagerCategory
        ExamPagerCategoryDTO examPagerCategoryDTO = examPagerCategoryMapper.toDto(examPagerCategory);
        restExamPagerCategoryMockMvc.perform(post("/api/exam-pager-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(examPagerCategoryDTO)))
            .andExpect(status().isCreated());

        // Validate the ExamPagerCategory in the database
        List<ExamPagerCategory> examPagerCategoryList = examPagerCategoryRepository.findAll();
        assertThat(examPagerCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        ExamPagerCategory testExamPagerCategory = examPagerCategoryList.get(examPagerCategoryList.size() - 1);
        assertThat(testExamPagerCategory.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testExamPagerCategory.getRemark()).isEqualTo(DEFAULT_REMARK);
        assertThat(testExamPagerCategory.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createExamPagerCategoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = examPagerCategoryRepository.findAll().size();

        // Create the ExamPagerCategory with an existing ID
        examPagerCategory.setId(1L);
        ExamPagerCategoryDTO examPagerCategoryDTO = examPagerCategoryMapper.toDto(examPagerCategory);

        // An entity with an existing ID cannot be created, so this API call must fail
        restExamPagerCategoryMockMvc.perform(post("/api/exam-pager-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(examPagerCategoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ExamPagerCategory in the database
        List<ExamPagerCategory> examPagerCategoryList = examPagerCategoryRepository.findAll();
        assertThat(examPagerCategoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllExamPagerCategories() throws Exception {
        // Initialize the database
        examPagerCategoryRepository.saveAndFlush(examPagerCategory);

        // Get all the examPagerCategoryList
        restExamPagerCategoryMockMvc.perform(get("/api/exam-pager-categories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(examPagerCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getExamPagerCategory() throws Exception {
        // Initialize the database
        examPagerCategoryRepository.saveAndFlush(examPagerCategory);

        // Get the examPagerCategory
        restExamPagerCategoryMockMvc.perform(get("/api/exam-pager-categories/{id}", examPagerCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(examPagerCategory.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.remark").value(DEFAULT_REMARK.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingExamPagerCategory() throws Exception {
        // Get the examPagerCategory
        restExamPagerCategoryMockMvc.perform(get("/api/exam-pager-categories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExamPagerCategory() throws Exception {
        // Initialize the database
        examPagerCategoryRepository.saveAndFlush(examPagerCategory);
        int databaseSizeBeforeUpdate = examPagerCategoryRepository.findAll().size();

        // Update the examPagerCategory
        ExamPagerCategory updatedExamPagerCategory = examPagerCategoryRepository.findOne(examPagerCategory.getId());
        // Disconnect from session so that the updates on updatedExamPagerCategory are not directly saved in db
        em.detach(updatedExamPagerCategory);
        updatedExamPagerCategory
            .name(UPDATED_NAME)
            .remark(UPDATED_REMARK)
            .status(UPDATED_STATUS);
        ExamPagerCategoryDTO examPagerCategoryDTO = examPagerCategoryMapper.toDto(updatedExamPagerCategory);

        restExamPagerCategoryMockMvc.perform(put("/api/exam-pager-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(examPagerCategoryDTO)))
            .andExpect(status().isOk());

        // Validate the ExamPagerCategory in the database
        List<ExamPagerCategory> examPagerCategoryList = examPagerCategoryRepository.findAll();
        assertThat(examPagerCategoryList).hasSize(databaseSizeBeforeUpdate);
        ExamPagerCategory testExamPagerCategory = examPagerCategoryList.get(examPagerCategoryList.size() - 1);
        assertThat(testExamPagerCategory.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testExamPagerCategory.getRemark()).isEqualTo(UPDATED_REMARK);
        assertThat(testExamPagerCategory.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingExamPagerCategory() throws Exception {
        int databaseSizeBeforeUpdate = examPagerCategoryRepository.findAll().size();

        // Create the ExamPagerCategory
        ExamPagerCategoryDTO examPagerCategoryDTO = examPagerCategoryMapper.toDto(examPagerCategory);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restExamPagerCategoryMockMvc.perform(put("/api/exam-pager-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(examPagerCategoryDTO)))
            .andExpect(status().isCreated());

        // Validate the ExamPagerCategory in the database
        List<ExamPagerCategory> examPagerCategoryList = examPagerCategoryRepository.findAll();
        assertThat(examPagerCategoryList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteExamPagerCategory() throws Exception {
        // Initialize the database
        examPagerCategoryRepository.saveAndFlush(examPagerCategory);
        int databaseSizeBeforeDelete = examPagerCategoryRepository.findAll().size();

        // Get the examPagerCategory
        restExamPagerCategoryMockMvc.perform(delete("/api/exam-pager-categories/{id}", examPagerCategory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ExamPagerCategory> examPagerCategoryList = examPagerCategoryRepository.findAll();
        assertThat(examPagerCategoryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExamPagerCategory.class);
        ExamPagerCategory examPagerCategory1 = new ExamPagerCategory();
        examPagerCategory1.setId(1L);
        ExamPagerCategory examPagerCategory2 = new ExamPagerCategory();
        examPagerCategory2.setId(examPagerCategory1.getId());
        assertThat(examPagerCategory1).isEqualTo(examPagerCategory2);
        examPagerCategory2.setId(2L);
        assertThat(examPagerCategory1).isNotEqualTo(examPagerCategory2);
        examPagerCategory1.setId(null);
        assertThat(examPagerCategory1).isNotEqualTo(examPagerCategory2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExamPagerCategoryDTO.class);
        ExamPagerCategoryDTO examPagerCategoryDTO1 = new ExamPagerCategoryDTO();
        examPagerCategoryDTO1.setId(1L);
        ExamPagerCategoryDTO examPagerCategoryDTO2 = new ExamPagerCategoryDTO();
        assertThat(examPagerCategoryDTO1).isNotEqualTo(examPagerCategoryDTO2);
        examPagerCategoryDTO2.setId(examPagerCategoryDTO1.getId());
        assertThat(examPagerCategoryDTO1).isEqualTo(examPagerCategoryDTO2);
        examPagerCategoryDTO2.setId(2L);
        assertThat(examPagerCategoryDTO1).isNotEqualTo(examPagerCategoryDTO2);
        examPagerCategoryDTO1.setId(null);
        assertThat(examPagerCategoryDTO1).isNotEqualTo(examPagerCategoryDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(examPagerCategoryMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(examPagerCategoryMapper.fromId(null)).isNull();
    }
}
