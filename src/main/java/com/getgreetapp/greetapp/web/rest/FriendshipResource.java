package com.getgreetapp.greetapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.getgreetapp.greetapp.domain.Friendship;
import com.getgreetapp.greetapp.repository.FriendshipRepository;
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
 * REST controller for managing Friendship.
 */
@RestController
@RequestMapping("/api")
public class FriendshipResource {

    private final Logger log = LoggerFactory.getLogger(FriendshipResource.class);

    private static final String ENTITY_NAME = "friendship";

    private final FriendshipRepository friendshipRepository;

    public FriendshipResource(FriendshipRepository friendshipRepository) {
        this.friendshipRepository = friendshipRepository;
    }

    /**
     * POST  /friendships : Create a new friendship.
     *
     * @param friendship the friendship to create
     * @return the ResponseEntity with status 201 (Created) and with body the new friendship, or with status 400 (Bad Request) if the friendship has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/friendships")
    @Timed
    public ResponseEntity<Friendship> createFriendship(@Valid @RequestBody Friendship friendship) throws URISyntaxException {
        log.debug("REST request to save Friendship : {}", friendship);
        if (friendship.getId() != null) {
            throw new BadRequestAlertException("A new friendship cannot already have an ID", ENTITY_NAME, "idexists");
        }
        System.out.println(friendship);
        Friendship result = friendshipRepository.save(friendship);
        return ResponseEntity.created(new URI("/api/friendships/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /friendships : Updates an existing friendship.
     *
     * @param friendship the friendship to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated friendship,
     * or with status 400 (Bad Request) if the friendship is not valid,
     * or with status 500 (Internal Server Error) if the friendship couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/friendships")
    @Timed
    public ResponseEntity<Friendship> updateFriendship(@Valid @RequestBody Friendship friendship) throws URISyntaxException {
        log.debug("REST request to update Friendship : {}", friendship);
        if (friendship.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Friendship result = friendshipRepository.save(friendship);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, friendship.getId().toString()))
            .body(result);
    }

    /**
     * GET  /friendships : get all the friendships.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of friendships in body
     */
    @GetMapping("/friendships")
    @Timed
    public List<Friendship> getAllFriendships() {
        log.debug("REST request to get all Friendships");
        return friendshipRepository.findAll();
    }

    /**
     * GET  /friendships-by-user/:id : get all the friendships by user.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of friendships in body
     */
    @GetMapping("/friendships-by-user/{userId}")
    @Timed
    public List<Friendship> getAllFriendshipsByUser(@PathVariable Long userId) {
        log.debug("REST request to get all Friendships");
        return friendshipRepository.findByUser(userId);
    }

    /**
     * GET  /friendships/:id : get the "id" friendship.
     *
     * @param id the id of the friendship to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the friendship, or with status 404 (Not Found)
     */
    @GetMapping("/friendships/{id}")
    @Timed
    public ResponseEntity<Friendship> getFriendship(@PathVariable Long id) {
        log.debug("REST request to get Friendship : {}", id);
        Optional<Friendship> friendship = friendshipRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(friendship);
    }

    /**
     * DELETE  /friendships/:id : delete the "id" friendship.
     *
     * @param id the id of the friendship to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/friendships/{id}")
    @Timed
    public ResponseEntity<Void> deleteFriendship(@PathVariable Long id) {
        log.debug("REST request to delete Friendship : {}", id);

        friendshipRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
