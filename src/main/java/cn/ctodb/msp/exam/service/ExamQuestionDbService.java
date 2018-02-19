package cn.ctodb.msp.exam.service;

import cn.ctodb.msp.exam.service.dto.ExamQuestionDbDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing ExamQuestionDb.
 */
public interface ExamQuestionDbService {

    /**
     * Save a examQuestionDb.
     *
     * @param examQuestionDbDTO the entity to save
     * @return the persisted entity
     */
    ExamQuestionDbDTO save(ExamQuestionDbDTO examQuestionDbDTO);

    /**
     * Get all the examQuestionDbs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ExamQuestionDbDTO> findAll(Pageable pageable);

    /**
     * Get the "id" examQuestionDb.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ExamQuestionDbDTO findOne(Long id);

    /**
     * Delete the "id" examQuestionDb.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
