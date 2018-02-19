package cn.ctodb.msp.exam.web.rest;

import com.codahale.metrics.annotation.Timed;
import cn.ctodb.msp.exam.service.ExamExamDataService;
import cn.ctodb.msp.exam.web.rest.errors.BadRequestAlertException;
import cn.ctodb.msp.exam.web.rest.util.HeaderUtil;
import cn.ctodb.msp.exam.web.rest.util.PaginationUtil;
import cn.ctodb.msp.exam.service.dto.ExamExamDataDTO;
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
 * REST controller for managing ExamExamData.
 */
@RestController
@RequestMapping("/api")
public class ExamExamDataResource {

    private final Logger log = LoggerFactory.getLogger(ExamExamDataResource.class);

    private static final String ENTITY_NAME = "examExamData";

    private final ExamExamDataService examExamDataService;

    public ExamExamDataResource(ExamExamDataService examExamDataService) {
        this.examExamDataService = examExamDataService;
    }

    /**
     * POST  /exam-exam-data : Create a new examExamData.
     *
     * @param examExamDataDTO the examExamDataDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new examExamDataDTO, or with status 400 (Bad Request) if the examExamData has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/exam-exam-data")
    @Timed
    public ResponseEntity<ExamExamDataDTO> createExamExamData(@RequestBody ExamExamDataDTO examExamDataDTO) throws URISyntaxException {
        log.debug("REST request to save ExamExamData : {}", examExamDataDTO);
        if (examExamDataDTO.getId() != null) {
            throw new BadRequestAlertException("A new examExamData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ExamExamDataDTO result = examExamDataService.save(examExamDataDTO);
        return ResponseEntity.created(new URI("/api/exam-exam-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /exam-exam-data : Updates an existing examExamData.
     *
     * @param examExamDataDTO the examExamDataDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated examExamDataDTO,
     * or with status 400 (Bad Request) if the examExamDataDTO is not valid,
     * or with status 500 (Internal Server Error) if the examExamDataDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/exam-exam-data")
    @Timed
    public ResponseEntity<ExamExamDataDTO> updateExamExamData(@RequestBody ExamExamDataDTO examExamDataDTO) throws URISyntaxException {
        log.debug("REST request to update ExamExamData : {}", examExamDataDTO);
        if (examExamDataDTO.getId() == null) {
            return createExamExamData(examExamDataDTO);
        }
        ExamExamDataDTO result = examExamDataService.save(examExamDataDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, examExamDataDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /exam-exam-data : get all the examExamData.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of examExamData in body
     */
    @GetMapping("/exam-exam-data")
    @Timed
    public ResponseEntity<List<ExamExamDataDTO>> getAllExamExamData(Pageable pageable) {
        log.debug("REST request to get a page of ExamExamData");
        Page<ExamExamDataDTO> page = examExamDataService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/exam-exam-data");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /exam-exam-data/:id : get the "id" examExamData.
     *
     * @param id the id of the examExamDataDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the examExamDataDTO, or with status 404 (Not Found)
     */
    @GetMapping("/exam-exam-data/{id}")
    @Timed
    public ResponseEntity<ExamExamDataDTO> getExamExamData(@PathVariable Long id) {
        log.debug("REST request to get ExamExamData : {}", id);
        ExamExamDataDTO examExamDataDTO = examExamDataService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(examExamDataDTO));
    }

    /**
     * DELETE  /exam-exam-data/:id : delete the "id" examExamData.
     *
     * @param id the id of the examExamDataDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/exam-exam-data/{id}")
    @Timed
    public ResponseEntity<Void> deleteExamExamData(@PathVariable Long id) {
        log.debug("REST request to delete ExamExamData : {}", id);
        examExamDataService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
