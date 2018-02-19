package cn.ctodb.msp.exam.service.dto;


import java.io.Serializable;
import java.util.Objects;
import cn.ctodb.msp.exam.domain.enumeration.Status;

/**
 * A DTO for the ExamQuestionDb entity.
 */
public class ExamQuestionDbDTO implements Serializable {

    private Long id;

    private String name;

    private String logo;

    private Status status;

    private String remark;

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

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ExamQuestionDbDTO examQuestionDbDTO = (ExamQuestionDbDTO) o;
        if(examQuestionDbDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), examQuestionDbDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ExamQuestionDbDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", logo='" + getLogo() + "'" +
            ", status='" + getStatus() + "'" +
            ", remark='" + getRemark() + "'" +
            "}";
    }
}
