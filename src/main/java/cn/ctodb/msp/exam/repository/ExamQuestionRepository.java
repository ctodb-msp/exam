package cn.ctodb.msp.exam.repository;

import cn.ctodb.msp.exam.domain.ExamQuestion;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ExamQuestion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExamQuestionRepository extends JpaRepository<ExamQuestion, Long> {

}
