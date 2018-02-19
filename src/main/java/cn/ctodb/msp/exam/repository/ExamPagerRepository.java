package cn.ctodb.msp.exam.repository;

import cn.ctodb.msp.exam.domain.ExamPager;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ExamPager entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExamPagerRepository extends JpaRepository<ExamPager, Long> {

}
