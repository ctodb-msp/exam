package cn.ctodb.msp.exam.web.rest;

import com.codahale.metrics.annotation.Timed;
import cn.ctodb.msp.exam.service.ExamPagerService;
import cn.ctodb.msp.exam.web.rest.errors.BadRequestAlertException;
import cn.ctodb.msp.exam.web.rest.util.HeaderUtil;
import cn.ctodb.msp.exam.web.rest.util.PaginationUtil;
import cn.ctodb.msp.exam.service.dto.ExamPagerDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ExamPager.
 */
@RestController
@RequestMapping("/api")
public class ExamPagerResource {

    private final Logger log = LoggerFactory.getLogger(ExamPagerResource.class);

    private static final String ENTITY_NAME = "examPager";

    private final ExamPagerService examPagerService;

    public ExamPagerResource(ExamPagerService examPagerService) {
        this.examPagerService = examPagerService;
    }

    /**
     * POST  /exam-pagers : Create a new examPager.
     *
     * @param examPagerDTO the examPagerDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new examPagerDTO, or with status 400 (Bad Request) if the examPager has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/exam-pagers")
    @Timed
    public ResponseEntity<ExamPagerDTO> createExamPager(@RequestBody ExamPagerDTO examPagerDTO) throws URISyntaxException {
        log.debug("REST request to save ExamPager : {}", examPagerDTO);
        if (examPagerDTO.getId() != null) {
            throw new BadRequestAlertException("A new examPager cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ExamPagerDTO result = examPagerService.save(examPagerDTO);
        return ResponseEntity.created(new URI("/api/exam-pagers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /exam-pagers : Updates an existing examPager.
     *
     * @param examPagerDTO the examPagerDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated examPagerDTO,
     * or with status 400 (Bad Request) if the examPagerDTO is not valid,
     * or with status 500 (Internal Server Error) if the examPagerDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/exam-pagers")
    @Timed
    public ResponseEntity<ExamPagerDTO> updateExamPager(@RequestBody ExamPagerDTO examPagerDTO) throws URISyntaxException {
        log.debug("REST request to update ExamPager : {}", examPagerDTO);
        if (examPagerDTO.getId() == null) {
            return createExamPager(examPagerDTO);
        }
        ExamPagerDTO result = examPagerService.save(examPagerDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, examPagerDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /exam-pagers : get all the examPagers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of examPagers in body
     */
    @GetMapping("/exam-pagers")
    @Timed
    public ResponseEntity<List<ExamPagerDTO>> getAllExamPagers(Pageable pageable) {
        log.debug("REST request to get a page of ExamPagers");
        Page<ExamPagerDTO> page = examPagerService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/exam-pagers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /exam-pagers/:id : get the "id" examPager.
     *
     * @param id the id of the examPagerDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the examPagerDTO, or with status 404 (Not Found)
     */
    @GetMapping("/exam-pagers/{id}")
    @Timed
    public ResponseEntity<ExamPagerDTO> getExamPager(@PathVariable Long id) {
        log.debug("REST request to get ExamPager : {}", id);
        ExamPagerDTO examPagerDTO = examPagerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(examPagerDTO));
    }

    /**
     * DELETE  /exam-pagers/:id : delete the "id" examPager.
     *
     * @param id the id of the examPagerDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/exam-pagers/{id}")
    @Timed
    public ResponseEntity<Void> deleteExamPager(@PathVariable Long id) {
        log.debug("REST request to delete ExamPager : {}", id);
        examPagerService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
