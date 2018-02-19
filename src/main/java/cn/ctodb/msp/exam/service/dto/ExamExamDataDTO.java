package cn.ctodb.msp.exam.service.dto;


import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;
import cn.ctodb.msp.exam.domain.enumeration.Status;

/**
 * A DTO for the ExamExamData entity.
 */
public class ExamExamDataDTO implements Serializable {

    private Long id;

    private ZonedDateTime startTime;

    private ZonedDateTime endTime;

    private Integer score;

    private Status status;

    @Lob
    private String data;

    @Lob
    private String check;

    private Long pagerId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(ZonedDateTime startTime) {
        this.startTime = startTime;
    }

    public ZonedDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(ZonedDateTime endTime) {
        this.endTime = endTime;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
    }

    public Long getPagerId() {
        return pagerId;
    }

    public void setPagerId(Long examPagerId) {
        this.pagerId = examPagerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ExamExamDataDTO examExamDataDTO = (ExamExamDataDTO) o;
        if(examExamDataDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), examExamDataDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ExamExamDataDTO{" +
            "id=" + getId() +
            ", startTime='" + getStartTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            ", score=" + getScore() +
            ", status='" + getStatus() + "'" +
            ", data='" + getData() + "'" +
            ", check='" + getCheck() + "'" +
            "}";
    }
}
