package cn.ctodb.msp.exam.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

import cn.ctodb.msp.exam.domain.enumeration.QuestionType;

import cn.ctodb.msp.exam.domain.enumeration.Status;

/**
 * A ExamQuestion.
 */
@Entity
@Table(name = "exam_question")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ExamQuestion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type")
    private QuestionType type;

    @Column(name = "question")
    private String question;

    @Column(name = "score")
    private Float score;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Lob
    @Column(name = "content")
    private String content;

    @Column(name = "jhi_key")
    private String key;

    @Lob
    @Column(name = "resolve")
    private String resolve;

    @Lob
    @Column(name = "data")
    private String data;

    @Column(name = "jhi_level")
    private Integer level;

    @ManyToOne
    private ExamQuestionDb db;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public QuestionType getType() {
        return type;
    }

    public ExamQuestion type(QuestionType type) {
        this.type = type;
        return this;
    }

    public void setType(QuestionType type) {
        this.type = type;
    }

    public String getQuestion() {
        return question;
    }

    public ExamQuestion question(String question) {
        this.question = question;
        return this;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Float getScore() {
        return score;
    }

    public ExamQuestion score(Float score) {
        this.score = score;
        return this;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public Status getStatus() {
        return status;
    }

    public ExamQuestion status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public ExamQuestion content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getKey() {
        return key;
    }

    public ExamQuestion key(String key) {
        this.key = key;
        return this;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getResolve() {
        return resolve;
    }

    public ExamQuestion resolve(String resolve) {
        this.resolve = resolve;
        return this;
    }

    public void setResolve(String resolve) {
        this.resolve = resolve;
    }

    public String getData() {
        return data;
    }

    public ExamQuestion data(String data) {
        this.data = data;
        return this;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Integer getLevel() {
        return level;
    }

    public ExamQuestion level(Integer level) {
        this.level = level;
        return this;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public ExamQuestionDb getDb() {
        return db;
    }

    public ExamQuestion db(ExamQuestionDb examQuestionDb) {
        this.db = examQuestionDb;
        return this;
    }

    public void setDb(ExamQuestionDb examQuestionDb) {
        this.db = examQuestionDb;
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
        ExamQuestion examQuestion = (ExamQuestion) o;
        if (examQuestion.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), examQuestion.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ExamQuestion{" +
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
