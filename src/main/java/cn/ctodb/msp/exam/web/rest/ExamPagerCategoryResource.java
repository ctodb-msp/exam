package cn.ctodb.msp.exam.web.rest;

import com.codahale.metrics.annotation.Timed;
import cn.ctodb.msp.exam.service.ExamPagerCategoryService;
import cn.ctodb.msp.exam.web.rest.errors.BadRequestAlertException;
import cn.ctodb.msp.exam.web.rest.util.HeaderUtil;
import cn.ctodb.msp.exam.web.rest.util.PaginationUtil;
import cn.ctodb.msp.exam.service.dto.ExamPagerCategoryDTO;
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
 * REST controller for managing ExamPagerCategory.
 */
@RestController
@RequestMapping("/api")
public class ExamPagerCategoryResource {

    private final Logger log = LoggerFactory.getLogger(ExamPagerCategoryResource.class);

    private static final String ENTITY_NAME = "examPagerCategory";

    private final ExamPagerCategoryService examPagerCategoryService;

    public ExamPagerCategoryResource(ExamPagerCategoryService examPagerCategoryService) {
        this.examPagerCategoryService = examPagerCategoryService;
    }

    /**
     * POST  /exam-pager-categories : Create a new examPagerCategory.
     *
     * @param examPagerCategoryDTO the examPagerCategoryDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new examPagerCategoryDTO, or with status 400 (Bad Request) if the examPagerCategory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/exam-pager-categories")
    @Timed
    public ResponseEntity<ExamPagerCategoryDTO> createExamPagerCategory(@RequestBody ExamPagerCategoryDTO examPagerCategoryDTO) throws URISyntaxException {
        log.debug("REST request to save ExamPagerCategory : {}", examPagerCategoryDTO);
        if (examPagerCategoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new examPagerCategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ExamPagerCategoryDTO result = examPagerCategoryService.save(examPagerCategoryDTO);
        return ResponseEntity.created(new URI("/api/exam-pager-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /exam-pager-categories : Updates an existing examPagerCategory.
     *
     * @param examPagerCategoryDTO the examPagerCategoryDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated examPagerCategoryDTO,
     * or with status 400 (Bad Request) if the examPagerCategoryDTO is not valid,
     * or with status 500 (Internal Server Error) if the examPagerCategoryDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/exam-pager-categories")
    @Timed
    public ResponseEntity<ExamPagerCategoryDTO> updateExamPagerCategory(@RequestBody ExamPagerCategoryDTO examPagerCategoryDTO) throws URISyntaxException {
        log.debug("REST request to update ExamPagerCategory : {}", examPagerCategoryDTO);
        if (examPagerCategoryDTO.getId() == null) {
            return createExamPagerCategory(examPagerCategoryDTO);
        }
        ExamPagerCategoryDTO result = examPagerCategoryService.save(examPagerCategoryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, examPagerCategoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /exam-pager-categories : get all the examPagerCategories.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of examPagerCategories in body
     */
    @GetMapping("/exam-pager-categories")
    @Timed
    public ResponseEntity<List<ExamPagerCategoryDTO>> getAllExamPagerCategories(Pageable pageable) {
        log.debug("REST request to get a page of ExamPagerCategories");
        Page<ExamPagerCategoryDTO> page = examPagerCategoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/exam-pager-categories");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /exam-pager-categories/:id : get the "id" examPagerCategory.
     *
     * @param id the id of the examPagerCategoryDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the examPagerCategoryDTO, or with status 404 (Not Found)
     */
    @GetMapping("/exam-pager-categories/{id}")
    @Timed
    public ResponseEntity<ExamPagerCategoryDTO> getExamPagerCategory(@PathVariable Long id) {
        log.debug("REST request to get ExamPagerCategory : {}", id);
        ExamPagerCategoryDTO examPagerCategoryDTO = examPagerCategoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(examPagerCategoryDTO));
    }

    /**
     * DELETE  /exam-pager-categories/:id : delete the "id" examPagerCategory.
     *
     * @param id the id of the examPagerCategoryDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/exam-pager-categories/{id}")
    @Timed
    public ResponseEntity<Void> deleteExamPagerCategory(@PathVariable Long id) {
        log.debug("REST request to delete ExamPagerCategory : {}", id);
        examPagerCategoryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
