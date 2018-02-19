package cn.ctodb.msp.exam.service.impl;

import cn.ctodb.msp.exam.service.ExamPagerCategoryService;
import cn.ctodb.msp.exam.domain.ExamPagerCategory;
import cn.ctodb.msp.exam.repository.ExamPagerCategoryRepository;
import cn.ctodb.msp.exam.service.dto.ExamPagerCategoryDTO;
import cn.ctodb.msp.exam.service.mapper.ExamPagerCategoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing ExamPagerCategory.
 */
@Service
@Transactional
public class ExamPagerCategoryServiceImpl implements ExamPagerCategoryService{

    private final Logger log = LoggerFactory.getLogger(ExamPagerCategoryServiceImpl.class);

    private final ExamPagerCategoryRepository examPagerCategoryRepository;

    private final ExamPagerCategoryMapper examPagerCategoryMapper;

    public ExamPagerCategoryServiceImpl(ExamPagerCategoryRepository examPagerCategoryRepository, ExamPagerCategoryMapper examPagerCategoryMapper) {
        this.examPagerCategoryRepository = examPagerCategoryRepository;
        this.examPagerCategoryMapper = examPagerCategoryMapper;
    }

    /**
     * Save a examPagerCategory.
     *
     * @param examPagerCategoryDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ExamPagerCategoryDTO save(ExamPagerCategoryDTO examPagerCategoryDTO) {
        log.debug("Request to save ExamPagerCategory : {}", examPagerCategoryDTO);
        ExamPagerCategory examPagerCategory = examPagerCategoryMapper.toEntity(examPagerCategoryDTO);
        examPagerCategory = examPagerCategoryRepository.save(examPagerCategory);
        return examPagerCategoryMapper.toDto(examPagerCategory);
    }

    /**
     * Get all the examPagerCategories.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ExamPagerCategoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ExamPagerCategories");
        return examPagerCategoryRepository.findAll(pageable)
            .map(examPagerCategoryMapper::toDto);
    }

    /**
     * Get one examPagerCategory by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ExamPagerCategoryDTO findOne(Long id) {
        log.debug("Request to get ExamPagerCategory : {}", id);
        ExamPagerCategory examPagerCategory = examPagerCategoryRepository.findOne(id);
        return examPagerCategoryMapper.toDto(examPagerCategory);
    }

    /**
     * Delete the examPagerCategory by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ExamPagerCategory : {}", id);
        examPagerCategoryRepository.delete(id);
    }
}
