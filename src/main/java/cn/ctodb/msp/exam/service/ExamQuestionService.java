package cn.ctodb.msp.exam.service;

import cn.ctodb.msp.exam.service.dto.ExamQuestionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing ExamQuestion.
 */
public interface ExamQuestionService {

    /**
     * Save a examQuestion.
     *
     * @param examQuestionDTO the entity to save
     * @return the persisted entity
     */
    ExamQuestionDTO save(ExamQuestionDTO examQuestionDTO);

    /**
     * Get all the examQuestions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ExamQuestionDTO> findAll(Pageable pageable);

    /**
     * Get the "id" examQuestion.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ExamQuestionDTO findOne(Long id);

    /**
     * Delete the "id" examQuestion.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
