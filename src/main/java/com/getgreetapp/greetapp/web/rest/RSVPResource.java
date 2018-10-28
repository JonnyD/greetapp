package com.getgreetapp.greetapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.getgreetapp.greetapp.domain.RSVP;
import com.getgreetapp.greetapp.repository.RSVPRepository;
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
 * REST controller for managing RSVP.
 */
@RestController
@RequestMapping("/api")
public class RSVPResource {

    private final Logger log = LoggerFactory.getLogger(RSVPResource.class);

    private static final String ENTITY_NAME = "rSVP";

    private final RSVPRepository rSVPRepository;

    public RSVPResource(RSVPRepository rSVPRepository) {
        this.rSVPRepository = rSVPRepository;
    }

    /**
     * POST  /rsvps : Create a new rSVP.
     *
     * @param rSVP the rSVP to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rSVP, or with status 400 (Bad Request) if the rSVP has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/rsvps")
    @Timed
    public ResponseEntity<RSVP> createRSVP(@Valid @RequestBody RSVP rSVP) throws URISyntaxException {
        log.debug("REST request to save RSVP : {}", rSVP);
        if (rSVP.getId() != null) {
            throw new BadRequestAlertException("A new rSVP cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RSVP result = rSVPRepository.save(rSVP);
        return ResponseEntity.created(new URI("/api/rsvps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rsvps : Updates an existing rSVP.
     *
     * @param rSVP the rSVP to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rSVP,
     * or with status 400 (Bad Request) if the rSVP is not valid,
     * or with status 500 (Internal Server Error) if the rSVP couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/rsvps")
    @Timed
    public ResponseEntity<RSVP> updateRSVP(@Valid @RequestBody RSVP rSVP) throws URISyntaxException {
        log.debug("REST request to update RSVP : {}", rSVP);
        if (rSVP.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RSVP result = rSVPRepository.save(rSVP);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, rSVP.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rsvps : get all the rSVPS.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of rSVPS in body
     */
    @GetMapping("/rsvps")
    @Timed
    public List<RSVP> getAllRSVPS() {
        log.debug("REST request to get all RSVPS");
        return rSVPRepository.findAll();
    }

    /**
     * GET  /rsvps/:id : get the "id" rSVP.
     *
     * @param id the id of the rSVP to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rSVP, or with status 404 (Not Found)
     */
    @GetMapping("/rsvps/{id}")
    @Timed
    public ResponseEntity<RSVP> getRSVP(@PathVariable Long id) {
        log.debug("REST request to get RSVP : {}", id);
        Optional<RSVP> rSVP = rSVPRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(rSVP);
    }

    /**
     * DELETE  /rsvps/:id : delete the "id" rSVP.
     *
     * @param id the id of the rSVP to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/rsvps/{id}")
    @Timed
    public ResponseEntity<Void> deleteRSVP(@PathVariable Long id) {
        log.debug("REST request to delete RSVP : {}", id);

        rSVPRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
