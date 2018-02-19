package cn.ctodb.msp.exam.repository;

import cn.ctodb.msp.exam.domain.ExamUserTest;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ExamUserTest entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExamUserTestRepository extends JpaRepository<ExamUserTest, Long> {

}
