package cn.ctodb.msp.exam.service.mapper;

import cn.ctodb.msp.exam.domain.*;
import cn.ctodb.msp.exam.service.dto.ExamPagerDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ExamPager and its DTO ExamPagerDTO.
 */
@Mapper(componentModel = "spring", uses = {ExamPagerCategoryMapper.class})
public interface ExamPagerMapper extends EntityMapper<ExamPagerDTO, ExamPager> {

    @Mapping(source = "category.id", target = "categoryId")
    ExamPagerDTO toDto(ExamPager examPager); 

    @Mapping(source = "categoryId", target = "category")
    ExamPager toEntity(ExamPagerDTO examPagerDTO);

    default ExamPager fromId(Long id) {
        if (id == null) {
            return null;
        }
        ExamPager examPager = new ExamPager();
        examPager.setId(id);
        return examPager;
    }
}
