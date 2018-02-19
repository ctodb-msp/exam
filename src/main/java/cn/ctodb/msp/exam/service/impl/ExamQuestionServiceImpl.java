package cn.ctodb.msp.exam.service.impl;

import cn.ctodb.msp.exam.service.ExamQuestionService;
import cn.ctodb.msp.exam.domain.ExamQuestion;
import cn.ctodb.msp.exam.repository.ExamQuestionRepository;
import cn.ctodb.msp.exam.service.dto.ExamQuestionDTO;
import cn.ctodb.msp.exam.service.mapper.ExamQuestionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing ExamQuestion.
 */
@Service
@Transactional
public class ExamQuestionServiceImpl implements ExamQuestionService{

    private final Logger log = LoggerFactory.getLogger(ExamQuestionServiceImpl.class);

    private final ExamQuestionRepository examQuestionRepository;

    private final ExamQuestionMapper examQuestionMapper;

    public ExamQuestionServiceImpl(ExamQuestionRepository examQuestionRepository, ExamQuestionMapper examQuestionMapper) {
        this.examQuestionRepository = examQuestionRepository;
        this.examQuestionMapper = examQuestionMapper;
    }

    /**
     * Save a examQuestion.
     *
     * @param examQuestionDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ExamQuestionDTO save(ExamQuestionDTO examQuestionDTO) {
        log.debug("Request to save ExamQuestion : {}", examQuestionDTO);
        ExamQuestion examQuestion = examQuestionMapper.toEntity(examQuestionDTO);
        examQuestion = examQuestionRepository.save(examQuestion);
        return examQuestionMapper.toDto(examQuestion);
    }

    /**
     * Get all the examQuestions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ExamQuestionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ExamQuestions");
        return examQuestionRepository.findAll(pageable)
            .map(examQuestionMapper::toDto);
    }

    /**
     * Get one examQuestion by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ExamQuestionDTO findOne(Long id) {
        log.debug("Request to get ExamQuestion : {}", id);
        ExamQuestion examQuestion = examQuestionRepository.findOne(id);
        return examQuestionMapper.toDto(examQuestion);
    }

    /**
     * Delete the examQuestion by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ExamQuestion : {}", id);
        examQuestionRepository.delete(id);
    }
}
