package cn.ctodb.msp.exam.service.dto;


import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the ExamUserTest entity.
 */
public class ExamUserTestDTO implements Serializable {

    private Long id;

    private String name;

    private Integer duration;

    private Integer totalScore;

    private Integer userScore;

    @Lob
    private String paper;

    @Lob
    private String answer;

    @Lob
    private String check;

    private ZonedDateTime testdate;

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

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Integer totalScore) {
        this.totalScore = totalScore;
    }

    public Integer getUserScore() {
        return userScore;
    }

    public void setUserScore(Integer userScore) {
        this.userScore = userScore;
    }

    public String getPaper() {
        return paper;
    }

    public void setPaper(String paper) {
        this.paper = paper;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
    }

    public ZonedDateTime getTestdate() {
        return testdate;
    }

    public void setTestdate(ZonedDateTime testdate) {
        this.testdate = testdate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ExamUserTestDTO examUserTestDTO = (ExamUserTestDTO) o;
        if(examUserTestDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), examUserTestDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ExamUserTestDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", duration=" + getDuration() +
            ", totalScore=" + getTotalScore() +
            ", userScore=" + getUserScore() +
            ", paper='" + getPaper() + "'" +
            ", answer='" + getAnswer() + "'" +
            ", check='" + getCheck() + "'" +
            ", testdate='" + getTestdate() + "'" +
            "}";
    }
}
