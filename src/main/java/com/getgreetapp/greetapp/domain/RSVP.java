package com.getgreetapp.greetapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A RSVP.
 */
@Entity
@Table(name = "rsvp")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RSVP implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "rsvp_response", nullable = false)
    private String rsvpResponse;

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

    public String getRsvpResponse() {
        return rsvpResponse;
    }

    public RSVP rsvpResponse(String rsvpResponse) {
        this.rsvpResponse = rsvpResponse;
        return this;
    }

    public void setRsvpResponse(String rsvpResponse) {
        this.rsvpResponse = rsvpResponse;
    }

    public Greet getGreet() {
        return greet;
    }

    public RSVP greet(Greet greet) {
        this.greet = greet;
        return this;
    }

    public void setGreet(Greet greet) {
        this.greet = greet;
    }

    public User getUser() {
        return user;
    }

    public RSVP user(User user) {
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
        RSVP rSVP = (RSVP) o;
        if (rSVP.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rSVP.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RSVP{" +
            "id=" + getId() +
            ", rsvpResponse='" + getRsvpResponse() + "'" +
            "}";
    }
}
