package cn.ctodb.msp.exam.service.dto;


import java.io.Serializable;
import java.util.Objects;
import cn.ctodb.msp.exam.domain.enumeration.Status;

/**
 * A DTO for the ExamPagerCategory entity.
 */
public class ExamPagerCategoryDTO implements Serializable {

    private Long id;

    private String name;

    private String remark;

    private Status status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ExamPagerCategoryDTO examPagerCategoryDTO = (ExamPagerCategoryDTO) o;
        if(examPagerCategoryDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), examPagerCategoryDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ExamPagerCategoryDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", remark='" + getRemark() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
