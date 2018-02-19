package cn.ctodb.msp.exam.service;

import cn.ctodb.msp.exam.service.dto.ExamExamDataDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing ExamExamData.
 */
public interface ExamExamDataService {

    /**
     * Save a examExamData.
     *
     * @param examExamDataDTO the entity to save
     * @return the persisted entity
     */
    ExamExamDataDTO save(ExamExamDataDTO examExamDataDTO);

    /**
     * Get all the examExamData.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ExamExamDataDTO> findAll(Pageable pageable);

    /**
     * Get the "id" examExamData.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ExamExamDataDTO findOne(Long id);

    /**
     * Delete the "id" examExamData.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
