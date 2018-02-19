package cn.ctodb.msp.exam.service.mapper;

import cn.ctodb.msp.exam.domain.*;
import cn.ctodb.msp.exam.service.dto.ExamUserTestDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ExamUserTest and its DTO ExamUserTestDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ExamUserTestMapper extends EntityMapper<ExamUserTestDTO, ExamUserTest> {

    

    

    default ExamUserTest fromId(Long id) {
        if (id == null) {
            return null;
        }
        ExamUserTest examUserTest = new ExamUserTest();
        examUserTest.setId(id);
        return examUserTest;
    }
}
