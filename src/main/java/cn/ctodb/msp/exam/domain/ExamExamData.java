package cn.ctodb.msp.exam.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import cn.ctodb.msp.exam.domain.enumeration.Status;

/**
 * A ExamExamData.
 */
@Entity
@Table(name = "exam_exam_data")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ExamExamData implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_time")
    private ZonedDateTime startTime;

    @Column(name = "end_time")
    private ZonedDateTime endTime;

    @Column(name = "score")
    private Integer score;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Lob
    @Column(name = "data")
    private String data;

    @Lob
    @Column(name = "jhi_check")
    private String check;

    @ManyToOne
    private ExamPager pager;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getStartTime() {
        return startTime;
    }

    public ExamExamData startTime(ZonedDateTime startTime) {
        this.startTime = startTime;
        return this;
    }

    public void setStartTime(ZonedDateTime startTime) {
        this.startTime = startTime;
    }

    public ZonedDateTime getEndTime() {
        return endTime;
    }

    public ExamExamData endTime(ZonedDateTime endTime) {
        this.endTime = endTime;
        return this;
    }

    public void setEndTime(ZonedDateTime endTime) {
        this.endTime = endTime;
    }

    public Integer getScore() {
        return score;
    }

    public ExamExamData score(Integer score) {
        this.score = score;
        return this;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Status getStatus() {
        return status;
    }

    public ExamExamData status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getData() {
        return data;
    }

    public ExamExamData data(String data) {
        this.data = data;
        return this;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getCheck() {
        return check;
    }

    public ExamExamData check(String check) {
        this.check = check;
        return this;
    }

    public void setCheck(String check) {
        this.check = check;
    }

    public ExamPager getPager() {
        return pager;
    }

    public ExamExamData pager(ExamPager examPager) {
        this.pager = examPager;
        return this;
    }

    public void setPager(ExamPager examPager) {
        this.pager = examPager;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ExamExamData examExamData = (ExamExamData) o;
        if (examExamData.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), examExamData.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ExamExamData{" +
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
