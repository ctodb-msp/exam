package cn.ctodb.msp.exam.service;

import cn.ctodb.msp.exam.service.dto.ExamPagerCategoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing ExamPagerCategory.
 */
public interface ExamPagerCategoryService {

    /**
     * Save a examPagerCategory.
     *
     * @param examPagerCategoryDTO the entity to save
     * @return the persisted entity
     */
    ExamPagerCategoryDTO save(ExamPagerCategoryDTO examPagerCategoryDTO);

    /**
     * Get all the examPagerCategories.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ExamPagerCategoryDTO> findAll(Pageable pageable);

    /**
     * Get the "id" examPagerCategory.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ExamPagerCategoryDTO findOne(Long id);

    /**
     * Delete the "id" examPagerCategory.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
