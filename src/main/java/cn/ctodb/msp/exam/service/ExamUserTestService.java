package cn.ctodb.msp.exam.service;

import cn.ctodb.msp.exam.service.dto.ExamUserTestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing ExamUserTest.
 */
public interface ExamUserTestService {

    /**
     * Save a examUserTest.
     *
     * @param examUserTestDTO the entity to save
     * @return the persisted entity
     */
    ExamUserTestDTO save(ExamUserTestDTO examUserTestDTO);

    /**
     * Get all the examUserTests.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ExamUserTestDTO> findAll(Pageable pageable);

    /**
     * Get the "id" examUserTest.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ExamUserTestDTO findOne(Long id);

    /**
     * Delete the "id" examUserTest.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
