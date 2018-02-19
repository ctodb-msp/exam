package cn.ctodb.msp.exam.service.mapper;

import cn.ctodb.msp.exam.domain.*;
import cn.ctodb.msp.exam.service.dto.ExamExamDataDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ExamExamData and its DTO ExamExamDataDTO.
 */
@Mapper(componentModel = "spring", uses = {ExamPagerMapper.class})
public interface ExamExamDataMapper extends EntityMapper<ExamExamDataDTO, ExamExamData> {

    @Mapping(source = "pager.id", target = "pagerId")
    ExamExamDataDTO toDto(ExamExamData examExamData); 

    @Mapping(source = "pagerId", target = "pager")
    ExamExamData toEntity(ExamExamDataDTO examExamDataDTO);

    default ExamExamData fromId(Long id) {
        if (id == null) {
            return null;
        }
        ExamExamData examExamData = new ExamExamData();
        examExamData.setId(id);
        return examExamData;
    }
}
