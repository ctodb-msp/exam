package cn.ctodb.msp.exam.service;

import cn.ctodb.msp.exam.service.dto.ExamPagerDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing ExamPager.
 */
public interface ExamPagerService {

    /**
     * Save a examPager.
     *
     * @param examPagerDTO the entity to save
     * @return the persisted entity
     */
    ExamPagerDTO save(ExamPagerDTO examPagerDTO);

    /**
     * Get all the examPagers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ExamPagerDTO> findAll(Pageable pageable);

    /**
     * Get the "id" examPager.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ExamPagerDTO findOne(Long id);

    /**
     * Delete the "id" examPager.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
