package cn.ctodb.msp.exam.web.rest;

import com.codahale.metrics.annotation.Timed;
import cn.ctodb.msp.exam.service.ExamQuestionDbService;
import cn.ctodb.msp.exam.web.rest.errors.BadRequestAlertException;
import cn.ctodb.msp.exam.web.rest.util.HeaderUtil;
import cn.ctodb.msp.exam.web.rest.util.PaginationUtil;
import cn.ctodb.msp.exam.service.dto.ExamQuestionDbDTO;
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
 * REST controller for managing ExamQuestionDb.
 */
@RestController
@RequestMapping("/api")
public class ExamQuestionDbResource {

    private final Logger log = LoggerFactory.getLogger(ExamQuestionDbResource.class);

    private static final String ENTITY_NAME = "examQuestionDb";

    private final ExamQuestionDbService examQuestionDbService;

    public ExamQuestionDbResource(ExamQuestionDbService examQuestionDbService) {
        this.examQuestionDbService = examQuestionDbService;
    }

    /**
     * POST  /exam-question-dbs : Create a new examQuestionDb.
     *
     * @param examQuestionDbDTO the examQuestionDbDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new examQuestionDbDTO, or with status 400 (Bad Request) if the examQuestionDb has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/exam-question-dbs")
    @Timed
    public ResponseEntity<ExamQuestionDbDTO> createExamQuestionDb(@RequestBody ExamQuestionDbDTO examQuestionDbDTO) throws URISyntaxException {
        log.debug("REST request to save ExamQuestionDb : {}", examQuestionDbDTO);
        if (examQuestionDbDTO.getId() != null) {
            throw new BadRequestAlertException("A new examQuestionDb cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ExamQuestionDbDTO result = examQuestionDbService.save(examQuestionDbDTO);
        return ResponseEntity.created(new URI("/api/exam-question-dbs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /exam-question-dbs : Updates an existing examQuestionDb.
     *
     * @param examQuestionDbDTO the examQuestionDbDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated examQuestionDbDTO,
     * or with status 400 (Bad Request) if the examQuestionDbDTO is not valid,
     * or with status 500 (Internal Server Error) if the examQuestionDbDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/exam-question-dbs")
    @Timed
    public ResponseEntity<ExamQuestionDbDTO> updateExamQuestionDb(@RequestBody ExamQuestionDbDTO examQuestionDbDTO) throws URISyntaxException {
        log.debug("REST request to update ExamQuestionDb : {}", examQuestionDbDTO);
        if (examQuestionDbDTO.getId() == null) {
            return createExamQuestionDb(examQuestionDbDTO);
        }
        ExamQuestionDbDTO result = examQuestionDbService.save(examQuestionDbDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, examQuestionDbDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /exam-question-dbs : get all the examQuestionDbs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of examQuestionDbs in body
     */
    @GetMapping("/exam-question-dbs")
    @Timed
    public ResponseEntity<List<ExamQuestionDbDTO>> getAllExamQuestionDbs(Pageable pageable) {
        log.debug("REST request to get a page of ExamQuestionDbs");
        Page<ExamQuestionDbDTO> page = examQuestionDbService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/exam-question-dbs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /exam-question-dbs/:id : get the "id" examQuestionDb.
     *
     * @param id the id of the examQuestionDbDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the examQuestionDbDTO, or with status 404 (Not Found)
     */
    @GetMapping("/exam-question-dbs/{id}")
    @Timed
    public ResponseEntity<ExamQuestionDbDTO> getExamQuestionDb(@PathVariable Long id) {
        log.debug("REST request to get ExamQuestionDb : {}", id);
        ExamQuestionDbDTO examQuestionDbDTO = examQuestionDbService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(examQuestionDbDTO));
    }

    /**
     * DELETE  /exam-question-dbs/:id : delete the "id" examQuestionDb.
     *
     * @param id the id of the examQuestionDbDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/exam-question-dbs/{id}")
    @Timed
    public ResponseEntity<Void> deleteExamQuestionDb(@PathVariable Long id) {
        log.debug("REST request to delete ExamQuestionDb : {}", id);
        examQuestionDbService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
