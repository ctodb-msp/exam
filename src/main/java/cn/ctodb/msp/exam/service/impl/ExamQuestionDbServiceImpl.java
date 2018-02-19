package cn.ctodb.msp.exam.service.impl;

import cn.ctodb.msp.exam.service.ExamQuestionDbService;
import cn.ctodb.msp.exam.domain.ExamQuestionDb;
import cn.ctodb.msp.exam.repository.ExamQuestionDbRepository;
import cn.ctodb.msp.exam.service.dto.ExamQuestionDbDTO;
import cn.ctodb.msp.exam.service.mapper.ExamQuestionDbMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing ExamQuestionDb.
 */
@Service
@Transactional
public class ExamQuestionDbServiceImpl implements ExamQuestionDbService{

    private final Logger log = LoggerFactory.getLogger(ExamQuestionDbServiceImpl.class);

    private final ExamQuestionDbRepository examQuestionDbRepository;

    private final ExamQuestionDbMapper examQuestionDbMapper;

    public ExamQuestionDbServiceImpl(ExamQuestionDbRepository examQuestionDbRepository, ExamQuestionDbMapper examQuestionDbMapper) {
        this.examQuestionDbRepository = examQuestionDbRepository;
        this.examQuestionDbMapper = examQuestionDbMapper;
    }

    /**
     * Save a examQuestionDb.
     *
     * @param examQuestionDbDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ExamQuestionDbDTO save(ExamQuestionDbDTO examQuestionDbDTO) {
        log.debug("Request to save ExamQuestionDb : {}", examQuestionDbDTO);
        ExamQuestionDb examQuestionDb = examQuestionDbMapper.toEntity(examQuestionDbDTO);
        examQuestionDb = examQuestionDbRepository.save(examQuestionDb);
        return examQuestionDbMapper.toDto(examQuestionDb);
    }

    /**
     * Get all the examQuestionDbs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ExamQuestionDbDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ExamQuestionDbs");
        return examQuestionDbRepository.findAll(pageable)
            .map(examQuestionDbMapper::toDto);
    }

    /**
     * Get one examQuestionDb by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ExamQuestionDbDTO findOne(Long id) {
        log.debug("Request to get ExamQuestionDb : {}", id);
        ExamQuestionDb examQuestionDb = examQuestionDbRepository.findOne(id);
        return examQuestionDbMapper.toDto(examQuestionDb);
    }

    /**
     * Delete the examQuestionDb by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ExamQuestionDb : {}", id);
        examQuestionDbRepository.delete(id);
    }
}
