package cn.ctodb.msp.exam.service.impl;

import cn.ctodb.msp.exam.service.ExamPagerService;
import cn.ctodb.msp.exam.domain.ExamPager;
import cn.ctodb.msp.exam.repository.ExamPagerRepository;
import cn.ctodb.msp.exam.service.dto.ExamPagerDTO;
import cn.ctodb.msp.exam.service.mapper.ExamPagerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing ExamPager.
 */
@Service
@Transactional
public class ExamPagerServiceImpl implements ExamPagerService{

    private final Logger log = LoggerFactory.getLogger(ExamPagerServiceImpl.class);

    private final ExamPagerRepository examPagerRepository;

    private final ExamPagerMapper examPagerMapper;

    public ExamPagerServiceImpl(ExamPagerRepository examPagerRepository, ExamPagerMapper examPagerMapper) {
        this.examPagerRepository = examPagerRepository;
        this.examPagerMapper = examPagerMapper;
    }

    /**
     * Save a examPager.
     *
     * @param examPagerDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ExamPagerDTO save(ExamPagerDTO examPagerDTO) {
        log.debug("Request to save ExamPager : {}", examPagerDTO);
        ExamPager examPager = examPagerMapper.toEntity(examPagerDTO);
        examPager = examPagerRepository.save(examPager);
        return examPagerMapper.toDto(examPager);
    }

    /**
     * Get all the examPagers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ExamPagerDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ExamPagers");
        return examPagerRepository.findAll(pageable)
            .map(examPagerMapper::toDto);
    }

    /**
     * Get one examPager by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ExamPagerDTO findOne(Long id) {
        log.debug("Request to get ExamPager : {}", id);
        ExamPager examPager = examPagerRepository.findOne(id);
        return examPagerMapper.toDto(examPager);
    }

    /**
     * Delete the examPager by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ExamPager : {}", id);
        examPagerRepository.delete(id);
    }
}
