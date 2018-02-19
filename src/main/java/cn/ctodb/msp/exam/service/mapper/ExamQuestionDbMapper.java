package cn.ctodb.msp.exam.service.mapper;

import cn.ctodb.msp.exam.domain.*;
import cn.ctodb.msp.exam.service.dto.ExamQuestionDbDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ExamQuestionDb and its DTO ExamQuestionDbDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ExamQuestionDbMapper extends EntityMapper<ExamQuestionDbDTO, ExamQuestionDb> {

    

    

    default ExamQuestionDb fromId(Long id) {
        if (id == null) {
            return null;
        }
        ExamQuestionDb examQuestionDb = new ExamQuestionDb();
        examQuestionDb.setId(id);
        return examQuestionDb;
    }
}
