package com.getgreetapp.greetapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Activity.
 */
@Entity
@Table(name = "activity")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Activity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "jhi_type", nullable = false)
    private String type;

    @NotNull
    @Column(name = "activity_component", nullable = false)
    private String activityComponent;

    @NotNull
    @Column(name = "object_id", nullable = false)
    private Integer objectId;

    @NotNull
    @Column(name = "message", nullable = false)
    private String message;

    @ManyToOne
    @JsonIgnoreProperties("")
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public Activity type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getActivityComponent() {
        return activityComponent;
    }

    public Activity activityComponent(String activityComponent) {
        this.activityComponent = activityComponent;
        return this;
    }

    public void setActivityComponent(String activityComponent) {
        this.activityComponent = activityComponent;
    }

    public Integer getObjectId() {
        return objectId;
    }

    public Activity objectId(Integer objectId) {
        this.objectId = objectId;
        return this;
    }

    public void setObjectId(Integer objectId) {
        this.objectId = objectId;
    }

    public String getMessage() {
        return message;
    }

    public Activity message(String message) {
        this.message = message;
        return this;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public Activity user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
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
        Activity activity = (Activity) o;
        if (activity.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), activity.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Activity{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", activityComponent='" + getActivityComponent() + "'" +
            ", objectId=" + getObjectId() +
            ", message='" + getMessage() + "'" +
            "}";
    }
}
