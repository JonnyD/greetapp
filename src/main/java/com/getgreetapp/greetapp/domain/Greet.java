package com.getgreetapp.greetapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * A Greet.
 */
@Entity
@Table(name = "greet")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Greet implements Serializable {

    private static final long serialVersionUID = 1L;

    public enum Privacy {PUBLIC, FRIENDS_ONLY, SECRET}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "longitude", precision = 10, scale = 2)
    private BigDecimal longitude;

    @Column(name = "latitude", precision = 10, scale = 2)
    private BigDecimal latitude;

    @NotNull
    @Column(name = "privacy", nullable = false)
    private String privacy;

    @ManyToOne
    @JsonIgnoreProperties("")
    private User user;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Gang gang;

    @OneToMany(mappedBy = "greet", fetch = FetchType.EAGER)
    private List<GreetInvitation> greetInvitations;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Greet title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public Greet description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public Greet longitude(BigDecimal longitude) {
        this.longitude = longitude;
        return this;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public Greet latitude(BigDecimal latitude) {
        this.latitude = latitude;
        return this;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public String getPrivacy() {
        return privacy;
    }

    public Greet privacy(String privacy) {
        this.privacy = privacy;
        return this;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }

    @JsonIgnore
    public boolean isPublic() {
        return (Privacy.PUBLIC.toString().equals(this.privacy));
    }

    @JsonIgnore
    public boolean isFriendsOnly() {
        return (Privacy.FRIENDS_ONLY.toString().equals(this.privacy));
    }

    @JsonIgnore
    public boolean isSecret() {
        return (Privacy.SECRET.toString().equals(this.privacy));
    }

    public User getUser() {
        return user;
    }

    public Greet user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Gang getGang() {
        return gang;
    }

    public Greet gang(Gang gang) {
        this.gang = gang;
        return this;
    }

    public void setGang(Gang gang) {
        this.gang = gang;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove


    public List<GreetInvitation> getGreetInvitations() {
        return greetInvitations;
    }

    public void setGreetInvitations(List<GreetInvitation> greetInvitations) {
        this.greetInvitations = greetInvitations;
    }

    public boolean isInvited(User user) {
        for (GreetInvitation invitation : this.greetInvitations) {
            if (invitation.getUser().equals(user)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Greet greet = (Greet) o;
        if (greet.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), greet.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Greet{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", longitude=" + getLongitude() +
            ", latitude=" + getLatitude() +
            ", privacy='" + getPrivacy() + "'" +
            "}";
    }
}
