package com.getgreetapp.greetapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.getgreetapp.greetapp.domain.GreetInvitation;
import com.getgreetapp.greetapp.domain.User;
import com.getgreetapp.greetapp.repository.GreetInvitationRepository;
import com.getgreetapp.greetapp.repository.UserRepository;
import com.getgreetapp.greetapp.security.SecurityUtils;
import com.getgreetapp.greetapp.specification.rules.CanCreateUpdateGreetInvitation;
import com.getgreetapp.greetapp.web.rest.errors.BadRequestAlertException;
import com.getgreetapp.greetapp.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing GreetInvitation.
 */
@RestController
@RequestMapping("/api")
public class GreetInvitationResource {

    private final Logger log = LoggerFactory.getLogger(GreetInvitationResource.class);

    private static final String ENTITY_NAME = "greetInvitation";

    private final GreetInvitationRepository greetInvitationRepository;

    private final UserRepository userRepository;

    public GreetInvitationResource(GreetInvitationRepository greetInvitationRepository, UserRepository userRepository) {
        this.greetInvitationRepository = greetInvitationRepository;
        this.userRepository = userRepository;
    }

    /**
     * POST  /greet-invitations : Create a new greetInvitation.
     *
     * @param greetInvitation the greetInvitation to create
     * @return the ResponseEntity with status 201 (Created) and with body the new greetInvitation, or with status 400 (Bad Request) if the greetInvitation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/greet-invitations")
    @Timed
    public ResponseEntity<GreetInvitation> createGreetInvitation(@Valid @RequestBody GreetInvitation greetInvitation) throws URISyntaxException {
        log.debug("REST request to save GreetInvitation : {}", greetInvitation);
        if (greetInvitation.getId() != null) {
            throw new BadRequestAlertException("A new greetInvitation cannot already have an ID", ENTITY_NAME, "idexists");
        }

        CanCreateUpdateGreetInvitation canCreateUpdateGreetInvitation = new CanCreateUpdateGreetInvitation(this.userRepository);

        if (canCreateUpdateGreetInvitation.isSatisfiedBy(greetInvitation)) {
            GreetInvitation result = greetInvitationRepository.save(greetInvitation);
            return ResponseEntity.created(new URI("/api/greet-invitations/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    /**
     * PUT  /greet-invitations : Updates an existing greetInvitation.
     *
     * @param greetInvitation the greetInvitation to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated greetInvitation,
     * or with status 400 (Bad Request) if the greetInvitation is not valid,
     * or with status 500 (Internal Server Error) if the greetInvitation couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/greet-invitations")
    @Timed
    public ResponseEntity<GreetInvitation> updateGreetInvitation(@Valid @RequestBody GreetInvitation greetInvitation) throws URISyntaxException {
        log.debug("REST request to update GreetInvitation : {}", greetInvitation);
        if (greetInvitation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }

        CanCreateUpdateGreetInvitation canCreateUpdateGreetInvitation = new CanCreateUpdateGreetInvitation(this.userRepository);

        if (canCreateUpdateGreetInvitation.isSatisfiedBy(greetInvitation)) {
            GreetInvitation result = greetInvitationRepository.save(greetInvitation);
            return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, greetInvitation.getId().toString()))
                .body(result);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    /**
     * GET  /greet-invitations : get all the greetInvitations.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of greetInvitations in body
     */
    @GetMapping("/greet-invitations")
    @Timed
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<GreetInvitation> getAllGreetInvitations() {
        log.debug("REST request to get all GreetInvitations");
        return greetInvitationRepository.findAll();
    }

    /**
     * GET  /greet-invitations-by-user/:userId : get all the greetInvitations by user.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of greetInvitations in body
     */
    @GetMapping("/greet-invitations-by-user/{userId}")
    @Timed
    public Object getAllGreetInvitationsByUser(@PathVariable Long userId) {
        log.debug("REST request to get all GreetInvitations by user");

        Optional<String> login = SecurityUtils.getCurrentUserLogin();
        User loggedInUser = userRepository.findOneByLogin(login.get()).get();

        if (userId != loggedInUser.getId()) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        return greetInvitationRepository.findByUser(userId);
    }

    /**
     * GET  /greet-invitations/:id : get the "id" greetInvitation.
     *
     * @param id the id of the greetInvitation to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the greetInvitation, or with status 404 (Not Found)
     */
    @GetMapping("/greet-invitations/{id}")
    @Timed
    public ResponseEntity<GreetInvitation> getGreetInvitation(@PathVariable Long id) {
        log.debug("REST request to get GreetInvitation : {}", id);
        Optional<GreetInvitation> greetInvitation = greetInvitationRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(greetInvitation);
    }

    /**
     * DELETE  /greet-invitations/:id : delete the "id" greetInvitation.
     *
     * @param id the id of the greetInvitation to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/greet-invitations/{id}")
    @Timed
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteGreetInvitation(@PathVariable Long id) {
        log.debug("REST request to delete GreetInvitation : {}", id);

        greetInvitationRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
