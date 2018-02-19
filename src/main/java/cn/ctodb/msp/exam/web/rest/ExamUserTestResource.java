package cn.ctodb.msp.exam.web.rest;

import com.codahale.metrics.annotation.Timed;
import cn.ctodb.msp.exam.service.ExamUserTestService;
import cn.ctodb.msp.exam.web.rest.errors.BadRequestAlertException;
import cn.ctodb.msp.exam.web.rest.util.HeaderUtil;
import cn.ctodb.msp.exam.web.rest.util.PaginationUtil;
import cn.ctodb.msp.exam.service.dto.ExamUserTestDTO;
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
 * REST controller for managing ExamUserTest.
 */
@RestController
@RequestMapping("/api")
public class ExamUserTestResource {

    private final Logger log = LoggerFactory.getLogger(ExamUserTestResource.class);

    private static final String ENTITY_NAME = "examUserTest";

    private final ExamUserTestService examUserTestService;

    public ExamUserTestResource(ExamUserTestService examUserTestService) {
        this.examUserTestService = examUserTestService;
    }

    /**
     * POST  /exam-user-tests : Create a new examUserTest.
     *
     * @param examUserTestDTO the examUserTestDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new examUserTestDTO, or with status 400 (Bad Request) if the examUserTest has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/exam-user-tests")
    @Timed
    public ResponseEntity<ExamUserTestDTO> createExamUserTest(@RequestBody ExamUserTestDTO examUserTestDTO) throws URISyntaxException {
        log.debug("REST request to save ExamUserTest : {}", examUserTestDTO);
        if (examUserTestDTO.getId() != null) {
            throw new BadRequestAlertException("A new examUserTest cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ExamUserTestDTO result = examUserTestService.save(examUserTestDTO);
        return ResponseEntity.created(new URI("/api/exam-user-tests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /exam-user-tests : Updates an existing examUserTest.
     *
     * @param examUserTestDTO the examUserTestDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated examUserTestDTO,
     * or with status 400 (Bad Request) if the examUserTestDTO is not valid,
     * or with status 500 (Internal Server Error) if the examUserTestDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/exam-user-tests")
    @Timed
    public ResponseEntity<ExamUserTestDTO> updateExamUserTest(@RequestBody ExamUserTestDTO examUserTestDTO) throws URISyntaxException {
        log.debug("REST request to update ExamUserTest : {}", examUserTestDTO);
        if (examUserTestDTO.getId() == null) {
            return createExamUserTest(examUserTestDTO);
        }
        ExamUserTestDTO result = examUserTestService.save(examUserTestDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, examUserTestDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /exam-user-tests : get all the examUserTests.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of examUserTests in body
     */
    @GetMapping("/exam-user-tests")
    @Timed
    public ResponseEntity<List<ExamUserTestDTO>> getAllExamUserTests(Pageable pageable) {
        log.debug("REST request to get a page of ExamUserTests");
        Page<ExamUserTestDTO> page = examUserTestService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/exam-user-tests");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /exam-user-tests/:id : get the "id" examUserTest.
     *
     * @param id the id of the examUserTestDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the examUserTestDTO, or with status 404 (Not Found)
     */
    @GetMapping("/exam-user-tests/{id}")
    @Timed
    public ResponseEntity<ExamUserTestDTO> getExamUserTest(@PathVariable Long id) {
        log.debug("REST request to get ExamUserTest : {}", id);
        ExamUserTestDTO examUserTestDTO = examUserTestService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(examUserTestDTO));
    }

    /**
     * DELETE  /exam-user-tests/:id : delete the "id" examUserTest.
     *
     * @param id the id of the examUserTestDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/exam-user-tests/{id}")
    @Timed
    public ResponseEntity<Void> deleteExamUserTest(@PathVariable Long id) {
        log.debug("REST request to delete ExamUserTest : {}", id);
        examUserTestService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
