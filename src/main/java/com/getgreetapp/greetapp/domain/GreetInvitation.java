package com.getgreetapp.greetapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A GreetInvitation.
 */
@Entity
@Table(name = "greet_invitation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class GreetInvitation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "greet_invitation_response", nullable = true)
    private String greetInvitationResponse;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Greet greet;

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

    public String getGreetInvitationResponse() {
        return greetInvitationResponse;
    }

    public GreetInvitation greetInvitationResponse(String greetInvitationResponse) {
        this.greetInvitationResponse = greetInvitationResponse;
        return this;
    }

    public void setGreetInvitationResponse(String greetInvitationResponse) {
        this.greetInvitationResponse = greetInvitationResponse;
    }

    public Greet getGreet() {
        return greet;
    }

    public GreetInvitation greet(Greet greet) {
        this.greet = greet;
        return this;
    }

    public void setGreet(Greet greet) {
        this.greet = greet;
    }

    public User getUser() {
        return user;
    }

    public GreetInvitation user(User user) {
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
        GreetInvitation greetInvitation = (GreetInvitation) o;
        if (greetInvitation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), greetInvitation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "GreetInvitation{" +
            "id=" + getId() +
            ", greetInvitationResponse='" + getGreetInvitationResponse() + "'" +
            "}";
    }
}
