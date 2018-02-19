package cn.ctodb.msp.exam.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A ExamUserTest.
 */
@Entity
@Table(name = "exam_user_test")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ExamUserTest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "total_score")
    private Integer totalScore;

    @Column(name = "user_score")
    private Integer userScore;

    @Lob
    @Column(name = "paper")
    private String paper;

    @Lob
    @Column(name = "answer")
    private String answer;

    @Lob
    @Column(name = "jhi_check")
    private String check;

    @Column(name = "testdate")
    private ZonedDateTime testdate;

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

    public ExamUserTest name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDuration() {
        return duration;
    }

    public ExamUserTest duration(Integer duration) {
        this.duration = duration;
        return this;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getTotalScore() {
        return totalScore;
    }

    public ExamUserTest totalScore(Integer totalScore) {
        this.totalScore = totalScore;
        return this;
    }

    public void setTotalScore(Integer totalScore) {
        this.totalScore = totalScore;
    }

    public Integer getUserScore() {
        return userScore;
    }

    public ExamUserTest userScore(Integer userScore) {
        this.userScore = userScore;
        return this;
    }

    public void setUserScore(Integer userScore) {
        this.userScore = userScore;
    }

    public String getPaper() {
        return paper;
    }

    public ExamUserTest paper(String paper) {
        this.paper = paper;
        return this;
    }

    public void setPaper(String paper) {
        this.paper = paper;
    }

    public String getAnswer() {
        return answer;
    }

    public ExamUserTest answer(String answer) {
        this.answer = answer;
        return this;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getCheck() {
        return check;
    }

    public ExamUserTest check(String check) {
        this.check = check;
        return this;
    }

    public void setCheck(String check) {
        this.check = check;
    }

    public ZonedDateTime getTestdate() {
        return testdate;
    }

    public ExamUserTest testdate(ZonedDateTime testdate) {
        this.testdate = testdate;
        return this;
    }

    public void setTestdate(ZonedDateTime testdate) {
        this.testdate = testdate;
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
        ExamUserTest examUserTest = (ExamUserTest) o;
        if (examUserTest.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), examUserTest.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ExamUserTest{" +
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
