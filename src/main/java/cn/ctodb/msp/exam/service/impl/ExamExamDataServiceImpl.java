package cn.ctodb.msp.exam.service.impl;

import cn.ctodb.msp.exam.service.ExamExamDataService;
import cn.ctodb.msp.exam.domain.ExamExamData;
import cn.ctodb.msp.exam.repository.ExamExamDataRepository;
import cn.ctodb.msp.exam.service.dto.ExamExamDataDTO;
import cn.ctodb.msp.exam.service.mapper.ExamExamDataMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing ExamExamData.
 */
@Service
@Transactional
public class ExamExamDataServiceImpl implements ExamExamDataService{

    private final Logger log = LoggerFactory.getLogger(ExamExamDataServiceImpl.class);

    private final ExamExamDataRepository examExamDataRepository;

    private final ExamExamDataMapper examExamDataMapper;

    public ExamExamDataServiceImpl(ExamExamDataRepository examExamDataRepository, ExamExamDataMapper examExamDataMapper) {
        this.examExamDataRepository = examExamDataRepository;
        this.examExamDataMapper = examExamDataMapper;
    }

    /**
     * Save a examExamData.
     *
     * @param examExamDataDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ExamExamDataDTO save(ExamExamDataDTO examExamDataDTO) {
        log.debug("Request to save ExamExamData : {}", examExamDataDTO);
        ExamExamData examExamData = examExamDataMapper.toEntity(examExamDataDTO);
        examExamData = examExamDataRepository.save(examExamData);
        return examExamDataMapper.toDto(examExamData);
    }

    /**
     * Get all the examExamData.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ExamExamDataDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ExamExamData");
        return examExamDataRepository.findAll(pageable)
            .map(examExamDataMapper::toDto);
    }

    /**
     * Get one examExamData by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ExamExamDataDTO findOne(Long id) {
        log.debug("Request to get ExamExamData : {}", id);
        ExamExamData examExamData = examExamDataRepository.findOne(id);
        return examExamDataMapper.toDto(examExamData);
    }

    /**
     * Delete the examExamData by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ExamExamData : {}", id);
        examExamDataRepository.delete(id);
    }
}
