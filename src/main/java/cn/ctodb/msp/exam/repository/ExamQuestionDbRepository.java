package cn.ctodb.msp.exam.repository;

import cn.ctodb.msp.exam.domain.ExamQuestionDb;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ExamQuestionDb entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExamQuestionDbRepository extends JpaRepository<ExamQuestionDb, Long> {

}
