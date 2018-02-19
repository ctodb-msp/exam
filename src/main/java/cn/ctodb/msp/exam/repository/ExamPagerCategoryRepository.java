package cn.ctodb.msp.exam.repository;

import cn.ctodb.msp.exam.domain.ExamPagerCategory;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ExamPagerCategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExamPagerCategoryRepository extends JpaRepository<ExamPagerCategory, Long> {

}
