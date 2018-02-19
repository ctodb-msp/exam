package cn.ctodb.msp.exam.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;
import cn.ctodb.msp.exam.domain.enumeration.QuestionType;
import cn.ctodb.msp.exam.domain.enumeration.Status;

/**
 * A DTO for the ExamQuestion entity.
 */
public class ExamQuestionDTO implements Serializable {

    private Long id;

    private QuestionType type;

    private String question;

    private Float score;

    private Status status;

    @Lob
    private String content;

    private String key;

    @Lob
    private String resolve;

    @Lob
    private String data;

    private Integer level;

    private Long dbId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public QuestionType getType() {
        return type;
    }

    public void setType(QuestionType type) {
        this.type = type;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getResolve() {
        return resolve;
    }

    public void setResolve(String resolve) {
        this.resolve = resolve;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Long getDbId() {
        return dbId;
    }

    public void setDbId(Long examQuestionDbId) {
        this.dbId = examQuestionDbId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ExamQuestionDTO examQuestionDTO = (ExamQuestionDTO) o;
        if(examQuestionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), examQuestionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ExamQuestionDTO{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", question='" + getQuestion() + "'" +
            ", score=" + getScore() +
            ", status='" + getStatus() + "'" +
            ", content='" + getContent() + "'" +
            ", key='" + getKey() + "'" +
            ", resolve='" + getResolve() + "'" +
            ", data='" + getData() + "'" +
            ", level=" + getLevel() +
            "}";
    }
}
