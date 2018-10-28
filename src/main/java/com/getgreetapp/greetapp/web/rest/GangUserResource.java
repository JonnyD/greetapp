package com.getgreetapp.greetapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.getgreetapp.greetapp.domain.GangUser;
import com.getgreetapp.greetapp.domain.User;
import com.getgreetapp.greetapp.repository.GangUserRepository;
import com.getgreetapp.greetapp.repository.UserRepository;
import com.getgreetapp.greetapp.web.rest.errors.BadRequestAlertException;
import com.getgreetapp.greetapp.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing GangUser.
 */
@RestController
@RequestMapping("/api")
public class GangUserResource {

    private final Logger log = LoggerFactory.getLogger(GangUserResource.class);

    private static final String ENTITY_NAME = "gangUser";

    private final GangUserRepository gangUserRepository;

    public GangUserResource(GangUserRepository gangUserRepository) {
        this.gangUserRepository = gangUserRepository;
    }

    /**
     * POST  /gang-users : Create a new gangUser.
     *
     * @param gangUser the gangUser to create
     * @return the ResponseEntity with status 201 (Created) and with body the new gangUser, or with status 400 (Bad Request) if the gangUser has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/gang-users")
    @Timed
    public ResponseEntity<GangUser> createGangUser(@RequestBody GangUser gangUser) throws URISyntaxException {
        log.debug("REST request to save GangUser : {}", gangUser);
        if (gangUser.getId() != null) {
            throw new BadRequestAlertException("A new gangUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GangUser result = gangUserRepository.save(gangUser);
        return ResponseEntity.created(new URI("/api/gang-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /gang-users : Updates an existing gangUser.
     *
     * @param gangUser the gangUser to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated gangUser,
     * or with status 400 (Bad Request) if the gangUser is not valid,
     * or with status 500 (Internal Server Error) if the gangUser couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/gang-users")
    @Timed
    public ResponseEntity<GangUser> updateGangUser(@Valid @RequestBody GangUser gangUser) throws URISyntaxException {
        log.debug("REST request to update GangUser : {}", gangUser);
        if (gangUser.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GangUser result = gangUserRepository.save(gangUser);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, gangUser.getId().toString()))
            .body(result);
    }

    /**
     * GET  /gang-users : get all the gangUsers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of gangUsers in body
     */
    @GetMapping("/gang-users")
    @Timed
    public List<GangUser> getAllGangUsers() {
        log.debug("REST request to get all GangUsers");
        return gangUserRepository.findAll();
    }

    /**
     * GET  /gang-users-by-group/:id : get all the gangUsers for group.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of gangUsers in body
     */
    @GetMapping("/gang-users-by-group/{groupId}")
    @Timed
    public List<GangUser> getAllGangUsers(@PathVariable Long groupId) {
        log.debug("REST request to get all GangUsers for group");
        return gangUserRepository.findByGroup(groupId);
    }

    /**
     * GET  /gang-users/:id : get the "id" gangUser.
     *
     * @param id the id of the gangUser to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the gangUser, or with status 404 (Not Found)
     */
    @GetMapping("/gang-users/{id}")
    @Timed
    public ResponseEntity<GangUser> getGangUser(@PathVariable Long id) {
        log.debug("REST request to get GangUser : {}", id);
        Optional<GangUser> gangUser = gangUserRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(gangUser);
    }

    /**
     * DELETE  /gang-users/:id : delete the "id" gangUser.
     *
     * @param id the id of the gangUser to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/gang-users/{id}")
    @Timed
    public ResponseEntity<Void> deleteGangUser(@PathVariable Long id) {
        log.debug("REST request to delete GangUser : {}", id);

        gangUserRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
