package cn.ctodb.msp.exam.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

import cn.ctodb.msp.exam.domain.enumeration.Status;

/**
 * A ExamPagerCategory.
 */
@Entity
@Table(name = "exam_pager_category")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ExamPagerCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "remark")
    private String remark;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

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

    public ExamPagerCategory name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public ExamPagerCategory remark(String remark) {
        this.remark = remark;
        return this;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Status getStatus() {
        return status;
    }

    public ExamPagerCategory status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
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
        ExamPagerCategory examPagerCategory = (ExamPagerCategory) o;
        if (examPagerCategory.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), examPagerCategory.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ExamPagerCategory{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", remark='" + getRemark() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
