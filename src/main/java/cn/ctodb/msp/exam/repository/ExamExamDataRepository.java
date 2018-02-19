package cn.ctodb.msp.exam.repository;

import cn.ctodb.msp.exam.domain.ExamExamData;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ExamExamData entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExamExamDataRepository extends JpaRepository<ExamExamData, Long> {

}
