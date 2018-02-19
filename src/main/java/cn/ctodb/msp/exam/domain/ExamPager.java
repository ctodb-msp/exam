package cn.ctodb.msp.exam.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A ExamPager.
 */
@Entity
@Table(name = "exam_pager")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ExamPager implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "start_time")
    private ZonedDateTime startTime;

    @Column(name = "end_time")
    private ZonedDateTime endTime;

    @Column(name = "show_time")
    private ZonedDateTime showTime;

    @Column(name = "total_score")
    private Integer totalScore;

    @Column(name = "count")
    private Integer count;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "user_time")
    private Integer userTime;

    @Lob
    @Column(name = "data")
    private String data;

    @ManyToOne
    private ExamPagerCategory category;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public ExamPager name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ZonedDateTime getStartTime() {
        return startTime;
    }

    public ExamPager startTime(ZonedDateTime startTime) {
        this.startTime = startTime;
        return this;
    }

    public void setStartTime(ZonedDateTime startTime) {
        this.startTime = startTime;
    }

    public ZonedDateTime getEndTime() {
        return endTime;
    }

    public ExamPager endTime(ZonedDateTime endTime) {
        this.endTime = endTime;
        return this;
    }

    public void setEndTime(ZonedDateTime endTime) {
        this.endTime = endTime;
    }

    public ZonedDateTime getShowTime() {
        return showTime;
    }

    public ExamPager showTime(ZonedDateTime showTime) {
        this.showTime = showTime;
        return this;
    }

    public void setShowTime(ZonedDateTime showTime) {
        this.showTime = showTime;
    }

    public Integer getTotalScore() {
        return totalScore;
    }

    public ExamPager totalScore(Integer totalScore) {
        this.totalScore = totalScore;
        return this;
    }

    public void setTotalScore(Integer totalScore) {
        this.totalScore = totalScore;
    }

    public Integer getCount() {
        return count;
    }

    public ExamPager count(Integer count) {
        this.count = count;
        return this;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getDuration() {
        return duration;
    }

    public ExamPager duration(Integer duration) {
        this.duration = duration;
        return this;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getUserTime() {
        return userTime;
    }

    public ExamPager userTime(Integer userTime) {
        this.userTime = userTime;
        return this;
    }

    public void setUserTime(Integer userTime) {
        this.userTime = userTime;
    }

    public String getData() {
        return data;
    }

    public ExamPager data(String data) {
        this.data = data;
        return this;
    }

    public void setData(String data) {
        this.data = data;
    }

    public ExamPagerCategory getCategory() {
        return category;
    }

    public ExamPager category(ExamPagerCategory examPagerCategory) {
        this.category = examPagerCategory;
        return this;
    }

    public void setCategory(ExamPagerCategory examPagerCategory) {
        this.category = examPagerCategory;
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
        ExamPager examPager = (ExamPager) o;
        if (examPager.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), examPager.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ExamPager{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", startTime='" + getStartTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            ", showTime='" + getShowTime() + "'" +
            ", totalScore=" + getTotalScore() +
            ", count=" + getCount() +
            ", duration=" + getDuration() +
            ", userTime=" + getUserTime() +
            ", data='" + getData() + "'" +
            "}";
    }
}
