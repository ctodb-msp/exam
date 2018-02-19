package cn.ctodb.msp.exam.service.mapper;

import cn.ctodb.msp.exam.domain.*;
import cn.ctodb.msp.exam.service.dto.ExamPagerCategoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ExamPagerCategory and its DTO ExamPagerCategoryDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ExamPagerCategoryMapper extends EntityMapper<ExamPagerCategoryDTO, ExamPagerCategory> {

    

    

    default ExamPagerCategory fromId(Long id) {
        if (id == null) {
            return null;
        }
        ExamPagerCategory examPagerCategory = new ExamPagerCategory();
        examPagerCategory.setId(id);
        return examPagerCategory;
    }
}
