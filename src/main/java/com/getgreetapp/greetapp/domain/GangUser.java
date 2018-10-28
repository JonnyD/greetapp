package com.getgreetapp.greetapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A GangUser.
 */
@Entity
@Table(name = "gang_user")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class GangUser implements Serializable {

    private static final long serialVersionUID = 1L;

    public enum Role {ADMIN, MEMBER}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "jhi_role", nullable = false)
    private String role;

    @ManyToOne(fetch = FetchType.EAGER)
    private Gang gang;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    public GangUser() {

    }

    public GangUser(String role, Gang gang, User user)
    {
        this.role = role;
        this.gang = gang;
        this.user = user;
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public GangUser role(String role) {
        this.role = role;
        return this;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Gang getGang() {
        return gang;
    }

    public GangUser gang(Gang gang) {
        this.gang = gang;
        return this;
    }

    public void setGang(Gang gang) {
        this.gang = gang;
    }

    public User getUser() {
        return user;
    }

    public GangUser user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @JsonIgnore
    public boolean isAdmin() {
        return (Role.ADMIN.toString().equals(this.role));
    }

    @JsonIgnore
    public boolean isMember() {
        return (Role.MEMBER.toString().equals(this.role));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GangUser gangUser = (GangUser) o;
        if (gangUser.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), gangUser.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "GangUser{" +
            "id=" + getId() +
            ", role='" + getRole() + "'" +
            "}";
    }
}
