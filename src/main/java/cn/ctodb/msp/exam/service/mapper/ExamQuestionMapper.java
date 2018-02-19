package cn.ctodb.msp.exam.service.mapper;

import cn.ctodb.msp.exam.domain.*;
import cn.ctodb.msp.exam.service.dto.ExamQuestionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ExamQuestion and its DTO ExamQuestionDTO.
 */
@Mapper(componentModel = "spring", uses = {ExamQuestionDbMapper.class})
public interface ExamQuestionMapper extends EntityMapper<ExamQuestionDTO, ExamQuestion> {

    @Mapping(source = "db.id", target = "dbId")
    ExamQuestionDTO toDto(ExamQuestion examQuestion); 

    @Mapping(source = "dbId", target = "db")
    ExamQuestion toEntity(ExamQuestionDTO examQuestionDTO);

    default ExamQuestion fromId(Long id) {
        if (id == null) {
            return null;
        }
        ExamQuestion examQuestion = new ExamQuestion();
        examQuestion.setId(id);
        return examQuestion;
    }
}
