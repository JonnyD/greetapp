package com.getgreetapp.greetapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A Gang.
 */
@Entity
@Table(name = "gang")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Gang implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull
    @Column(name = "longitude", precision = 10, scale = 2, nullable = false)
    private BigDecimal longitude;

    @NotNull
    @Column(name = "latitude", precision = 10, scale = 2, nullable = false)
    private BigDecimal latitude;

    @NotNull
    @Column(name = "membership_approval", nullable = false)
    private String membershipApproval;

    @NotNull
    @Column(name = "privacy", nullable = false)
    private String privacy;

    public Gang() {}

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

    public Gang name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Gang description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public Gang longitude(BigDecimal longitude) {
        this.longitude = longitude;
        return this;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public Gang latitude(BigDecimal latitude) {
        this.latitude = latitude;
        return this;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public String getMembershipApproval() {
        return membershipApproval;
    }

    public Gang membershipApproval(String membershipApproval) {
        this.membershipApproval = membershipApproval;
        return this;
    }

    public void setMembershipApproval(String membershipApproval) {
        this.membershipApproval = membershipApproval;
    }

    public String getPrivacy() {
        return privacy;
    }

    public Gang privacy(String privacy) {
        this.privacy = privacy;
        return this;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
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
        Gang gang = (Gang) o;
        if (gang.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), gang.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Gang{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", longitude=" + getLongitude() +
            ", latitude=" + getLatitude() +
            ", membershipApproval='" + getMembershipApproval() + "'" +
            ", privacy='" + getPrivacy() + "'" +
            "}";
    }
}
