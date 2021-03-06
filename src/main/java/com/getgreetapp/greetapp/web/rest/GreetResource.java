package com.getgreetapp.greetapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.getgreetapp.greetapp.domain.Gang;
import com.getgreetapp.greetapp.domain.Greet;
import com.getgreetapp.greetapp.domain.NearbyGreet;
import com.getgreetapp.greetapp.domain.User;
import com.getgreetapp.greetapp.repository.GangRepository;
import com.getgreetapp.greetapp.repository.GreetRepository;
import com.getgreetapp.greetapp.repository.UserRepository;
import com.getgreetapp.greetapp.security.SecurityUtils;
import com.getgreetapp.greetapp.service.FriendshipService;
import com.getgreetapp.greetapp.service.GangService;
import com.getgreetapp.greetapp.service.GreetService;
import com.getgreetapp.greetapp.service.UserService;
import com.getgreetapp.greetapp.specification.rules.CanViewGang;
import com.getgreetapp.greetapp.specification.rules.CanViewGreet;
import com.getgreetapp.greetapp.specification.rules.CanViewGreetsByGang;
import com.getgreetapp.greetapp.specification.rules.IsHostOfGreet;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * REST controller for managing Greet.
 */
@RestController
@RequestMapping("/api")
public class GreetResource {

    private final Logger log = LoggerFactory.getLogger(GreetResource.class);

    private static final String ENTITY_NAME = "greet";

    private final GreetService greetService;

    private final UserService userService;

    private final UserRepository userRepository;

    private final GangService gangService;

    private final FriendshipService friendshipService;

    public GreetResource(GreetService greetService,
                         UserService userService,
                         UserRepository userRepository,
                         GangService gangService,
                         FriendshipService friendshipService) {
        this.greetService = greetService;
        this.userService = userService;
        this.userRepository = userRepository;
        this.gangService = gangService;
        this.friendshipService = friendshipService;
    }

    /**
     * POST  /greets : Create a new greet.
     *
     * @param greet the greet to create
     * @return the ResponseEntity with status 201 (Created) and with body the new greet, or with status 400 (Bad Request) if the greet has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/greets")
    @Timed
    public ResponseEntity<Greet> createGreet(@Valid @RequestBody Greet greet) throws URISyntaxException {
        log.debug("REST request to save Greet : {}", greet);
        if (greet.getId() != null) {
            throw new BadRequestAlertException("A new greet cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Greet result = greetService.save(greet);
        return ResponseEntity.created(new URI("/api/greets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /greets : Updates an existing greet.
     *
     * @param greet the greet to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated greet,
     * or with status 400 (Bad Request) if the greet is not valid,
     * or with status 500 (Internal Server Error) if the greet couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/greets")
    @Timed
    public ResponseEntity<Greet> updateGreet(@Valid @RequestBody Greet greet) throws URISyntaxException {
        log.debug("REST request to update Greet : {}", greet);
        if (greet.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }

        Optional<String> login = SecurityUtils.getCurrentUserLogin();
        User loggedInUser = userRepository.findOneByLogin(login.get()).get();

        IsHostOfGreet isHostOfGreet = new IsHostOfGreet(loggedInUser);

        if (isHostOfGreet.isSatisfiedBy(greet)) {
            Greet result = greetService.save(greet);
            return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, greet.getId().toString()))
                .body(result);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

    }

    /**
     * GET  /greets : get all the greets.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of greets in body
     */
    @GetMapping("/greets")
    @Timed
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<Greet> getAllGreets() {
        log.debug("REST request to get all Greets");
        return greetService.getAll();
    }

    /**
     * GET  /get-nearby-greets : get nearby greets.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of greets in body
     */
    @GetMapping("/get-nearby-greets")
    @Timed
    public List<NearbyGreet> getNearbyGreets(Double longitude, Double latitude, int radius) {
        log.debug("REST request to get nearby Greets");
        return greetService.getNearbyGreets(longitude, latitude, radius);
    }

    /**
     * GET  /greets-by-user/:userId : get all the greets by user.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of greets in body
     */
    @GetMapping("/greets-by-user/{userId}")
    @Timed
    public Object getAllGreetsByUser(@PathVariable Long userId) {
        log.debug("REST request to get all Greets by user");

        Optional<String> login = SecurityUtils.getCurrentUserLogin();
        User loggedInUser = userRepository.findOneByLogin(login.get()).get();

        if (userId != loggedInUser.getId()) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        return greetService.getByUser(userId);
    }

    /**
     * GET  /greets-by-user/:groupId : get all the greets by group.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of greets in body
     */
    @GetMapping("/greets-by-group/{groupId}")
    @Timed
    public Object getAllGreetsByGroup(@PathVariable Long groupId) {
        log.debug("REST request to get all Greets by user");
        List<Greet> greets = greetService.getByGang(groupId);

        Optional<Gang> optionalGang = this.gangService.getById(groupId);
        Gang gang = optionalGang.get();

        CanViewGang canViewGang = new CanViewGang(this.userService);
        if (!canViewGang.isSatisfiedBy(gang)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        CanViewGreetsByGang canViewGreetsByGang = new CanViewGreetsByGang(this.userRepository);

        List<Greet> greetsToShow = new ArrayList<Greet>();
        for (Greet greet : greets) {
            if (canViewGreetsByGang.isSatisfiedBy(greet)) {
                greetsToShow.add(greet);
            }
        }

        return greetsToShow;
    }

    /**
     * GET  /greets/:id : get the "id" greet.
     *
     * @param id the id of the greet to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the greet, or with status 404 (Not Found)
     */
    @GetMapping("/greets/{id}")
    @Timed
    public ResponseEntity<Greet> getGreet(@PathVariable Long id) {
        log.debug("REST request to get Greet : {}", id);
        Optional<Greet> optionalGreet = greetService.getById(id);
        Greet greet = optionalGreet.get();

        CanViewGreet canViewGreet = new CanViewGreet(this.userRepository, this.friendshipService);

        if (canViewGreet.isSatisfiedBy(greet)) {
            return ResponseUtil.wrapOrNotFound(optionalGreet);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

    }

    /**
     * DELETE  /greets/:id : delete the "id" greet.
     *
     * @param id the id of the greet to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/greets/{id}")
    @Timed
    public ResponseEntity<Void> deleteGreet(@PathVariable Long id) {
        log.debug("REST request to delete Greet : {}", id);

        greetService.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
