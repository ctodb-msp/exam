package cn.ctodb.msp.exam.service.dto;


import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the ExamPager entity.
 */
public class ExamPagerDTO implements Serializable {

    private Long id;

    private String name;

    private ZonedDateTime startTime;

    private ZonedDateTime endTime;

    private ZonedDateTime showTime;

    private Integer totalScore;

    private Integer count;

    private Integer duration;

    private Integer userTime;

    @Lob
    private String data;

    private Long categoryId;

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

    public ZonedDateTime getShowTime() {
        return showTime;
    }

    public void setShowTime(ZonedDateTime showTime) {
        this.showTime = showTime;
    }

    public Integer getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Integer totalScore) {
        this.totalScore = totalScore;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getUserTime() {
        return userTime;
    }

    public void setUserTime(Integer userTime) {
        this.userTime = userTime;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long examPagerCategoryId) {
        this.categoryId = examPagerCategoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ExamPagerDTO examPagerDTO = (ExamPagerDTO) o;
        if(examPagerDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), examPagerDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ExamPagerDTO{" +
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
