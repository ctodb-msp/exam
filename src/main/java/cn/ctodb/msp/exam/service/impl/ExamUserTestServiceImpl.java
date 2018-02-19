package cn.ctodb.msp.exam.service.impl;

import cn.ctodb.msp.exam.service.ExamUserTestService;
import cn.ctodb.msp.exam.domain.ExamUserTest;
import cn.ctodb.msp.exam.repository.ExamUserTestRepository;
import cn.ctodb.msp.exam.service.dto.ExamUserTestDTO;
import cn.ctodb.msp.exam.service.mapper.ExamUserTestMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing ExamUserTest.
 */
@Service
@Transactional
public class ExamUserTestServiceImpl implements ExamUserTestService{

    private final Logger log = LoggerFactory.getLogger(ExamUserTestServiceImpl.class);

    private final ExamUserTestRepository examUserTestRepository;

    private final ExamUserTestMapper examUserTestMapper;

    public ExamUserTestServiceImpl(ExamUserTestRepository examUserTestRepository, ExamUserTestMapper examUserTestMapper) {
        this.examUserTestRepository = examUserTestRepository;
        this.examUserTestMapper = examUserTestMapper;
    }

    /**
     * Save a examUserTest.
     *
     * @param examUserTestDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ExamUserTestDTO save(ExamUserTestDTO examUserTestDTO) {
        log.debug("Request to save ExamUserTest : {}", examUserTestDTO);
        ExamUserTest examUserTest = examUserTestMapper.toEntity(examUserTestDTO);
        examUserTest = examUserTestRepository.save(examUserTest);
        return examUserTestMapper.toDto(examUserTest);
    }

    /**
     * Get all the examUserTests.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ExamUserTestDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ExamUserTests");
        return examUserTestRepository.findAll(pageable)
            .map(examUserTestMapper::toDto);
    }

    /**
     * Get one examUserTest by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ExamUserTestDTO findOne(Long id) {
        log.debug("Request to get ExamUserTest : {}", id);
        ExamUserTest examUserTest = examUserTestRepository.findOne(id);
        return examUserTestMapper.toDto(examUserTest);
    }

    /**
     * Delete the examUserTest by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ExamUserTest : {}", id);
        examUserTestRepository.delete(id);
    }
}
