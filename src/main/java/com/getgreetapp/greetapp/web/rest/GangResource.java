package com.getgreetapp.greetapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.getgreetapp.greetapp.domain.Gang;
import com.getgreetapp.greetapp.repository.GangRepository;
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
 * REST controller for managing Gang.
 */
@RestController
@RequestMapping("/api")
public class GangResource {

    private final Logger log = LoggerFactory.getLogger(GangResource.class);

    private static final String ENTITY_NAME = "gang";

    private final GangRepository gangRepository;

    public GangResource(GangRepository gangRepository) {
        this.gangRepository = gangRepository;
    }

    /**
     * POST  /gangs : Create a new gang.
     *
     * @param gang the gang to create
     * @return the ResponseEntity with status 201 (Created) and with body the new gang, or with status 400 (Bad Request) if the gang has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/gangs")
    @Timed
    public ResponseEntity<Gang> createGang(@Valid @RequestBody Gang gang) throws URISyntaxException {
        log.debug("REST request to save Gang : {}", gang);
        if (gang.getId() != null) {
            throw new BadRequestAlertException("A new gang cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Gang result = gangRepository.save(gang);
        return ResponseEntity.created(new URI("/api/gangs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /gangs : Updates an existing gang.
     *
     * @param gang the gang to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated gang,
     * or with status 400 (Bad Request) if the gang is not valid,
     * or with status 500 (Internal Server Error) if the gang couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/gangs")
    @Timed
    public ResponseEntity<Gang> updateGang(@Valid @RequestBody Gang gang) throws URISyntaxException {
        log.debug("REST request to update Gang : {}", gang);
        if (gang.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Gang result = gangRepository.save(gang);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, gang.getId().toString()))
            .body(result);
    }

    /**
     * GET  /gangs : get all the gangs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of gangs in body
     */
    @GetMapping("/gangs")
    @Timed
    public List<Gang> getAllGangs() {
        log.debug("REST request to get all Gangs");
        return gangRepository.findAll();
    }

    /**
     * GET  /gangs/:id : get the "id" gang.
     *
     * @param id the id of the gang to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the gang, or with status 404 (Not Found)
     */
    @GetMapping("/gangs/{id}")
    @Timed
    public ResponseEntity<Gang> getGang(@PathVariable Long id) {
        log.debug("REST request to get Gang : {}", id);
        Optional<Gang> gang = gangRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(gang);
    }

    /**
     * DELETE  /gangs/:id : delete the "id" gang.
     *
     * @param id the id of the gang to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/gangs/{id}")
    @Timed
    public ResponseEntity<Void> deleteGang(@PathVariable Long id) {
        log.debug("REST request to delete Gang : {}", id);

        gangRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
