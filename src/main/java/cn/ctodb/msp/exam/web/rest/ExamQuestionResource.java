package cn.ctodb.msp.exam.web.rest;

import com.codahale.metrics.annotation.Timed;
import cn.ctodb.msp.exam.service.ExamQuestionService;
import cn.ctodb.msp.exam.web.rest.errors.BadRequestAlertException;
import cn.ctodb.msp.exam.web.rest.util.HeaderUtil;
import cn.ctodb.msp.exam.web.rest.util.PaginationUtil;
import cn.ctodb.msp.exam.service.dto.ExamQuestionDTO;
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
 * REST controller for managing ExamQuestion.
 */
@RestController
@RequestMapping("/api")
public class ExamQuestionResource {

    private final Logger log = LoggerFactory.getLogger(ExamQuestionResource.class);

    private static final String ENTITY_NAME = "examQuestion";

    private final ExamQuestionService examQuestionService;

    public ExamQuestionResource(ExamQuestionService examQuestionService) {
        this.examQuestionService = examQuestionService;
    }

    /**
     * POST  /exam-questions : Create a new examQuestion.
     *
     * @param examQuestionDTO the examQuestionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new examQuestionDTO, or with status 400 (Bad Request) if the examQuestion has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/exam-questions")
    @Timed
    public ResponseEntity<ExamQuestionDTO> createExamQuestion(@RequestBody ExamQuestionDTO examQuestionDTO) throws URISyntaxException {
        log.debug("REST request to save ExamQuestion : {}", examQuestionDTO);
        if (examQuestionDTO.getId() != null) {
            throw new BadRequestAlertException("A new examQuestion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ExamQuestionDTO result = examQuestionService.save(examQuestionDTO);
        return ResponseEntity.created(new URI("/api/exam-questions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /exam-questions : Updates an existing examQuestion.
     *
     * @param examQuestionDTO the examQuestionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated examQuestionDTO,
     * or with status 400 (Bad Request) if the examQuestionDTO is not valid,
     * or with status 500 (Internal Server Error) if the examQuestionDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/exam-questions")
    @Timed
    public ResponseEntity<ExamQuestionDTO> updateExamQuestion(@RequestBody ExamQuestionDTO examQuestionDTO) throws URISyntaxException {
        log.debug("REST request to update ExamQuestion : {}", examQuestionDTO);
        if (examQuestionDTO.getId() == null) {
            return createExamQuestion(examQuestionDTO);
        }
        ExamQuestionDTO result = examQuestionService.save(examQuestionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, examQuestionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /exam-questions : get all the examQuestions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of examQuestions in body
     */
    @GetMapping("/exam-questions")
    @Timed
    public ResponseEntity<List<ExamQuestionDTO>> getAllExamQuestions(Pageable pageable) {
        log.debug("REST request to get a page of ExamQuestions");
        Page<ExamQuestionDTO> page = examQuestionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/exam-questions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /exam-questions/:id : get the "id" examQuestion.
     *
     * @param id the id of the examQuestionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the examQuestionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/exam-questions/{id}")
    @Timed
    public ResponseEntity<ExamQuestionDTO> getExamQuestion(@PathVariable Long id) {
        log.debug("REST request to get ExamQuestion : {}", id);
        ExamQuestionDTO examQuestionDTO = examQuestionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(examQuestionDTO));
    }

    /**
     * DELETE  /exam-questions/:id : delete the "id" examQuestion.
     *
     * @param id the id of the examQuestionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/exam-questions/{id}")
    @Timed
    public ResponseEntity<Void> deleteExamQuestion(@PathVariable Long id) {
        log.debug("REST request to delete ExamQuestion : {}", id);
        examQuestionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
