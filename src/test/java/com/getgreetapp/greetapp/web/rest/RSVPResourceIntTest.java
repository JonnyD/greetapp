package com.getgreetapp.greetapp.web.rest;

import com.getgreetapp.greetapp.GreetappApp;

import com.getgreetapp.greetapp.domain.RSVP;
import com.getgreetapp.greetapp.repository.RSVPRepository;
import com.getgreetapp.greetapp.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;


import static com.getgreetapp.greetapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the RSVPResource REST controller.
 *
 * @see RSVPResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GreetappApp.class)
public class RSVPResourceIntTest {

    private static final String DEFAULT_RSVP_RESPONSE = "AAAAAAAAAA";
    private static final String UPDATED_RSVP_RESPONSE = "BBBBBBBBBB";

    @Autowired
    private RSVPRepository rSVPRepository;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRSVPMockMvc;

    private RSVP rSVP;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RSVPResource rSVPResource = new RSVPResource(rSVPRepository);
        this.restRSVPMockMvc = MockMvcBuilders.standaloneSetup(rSVPResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RSVP createEntity(EntityManager em) {
        RSVP rSVP = new RSVP()
            .rsvpResponse(DEFAULT_RSVP_RESPONSE);
        return rSVP;
    }

    @Before
    public void initTest() {
        rSVP = createEntity(em);
    }

    @Test
    @Transactional
    public void createRSVP() throws Exception {
        int databaseSizeBeforeCreate = rSVPRepository.findAll().size();

        // Create the RSVP
        restRSVPMockMvc.perform(post("/api/rsvps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rSVP)))
            .andExpect(status().isCreated());

        // Validate the RSVP in the database
        List<RSVP> rSVPList = rSVPRepository.findAll();
        assertThat(rSVPList).hasSize(databaseSizeBeforeCreate + 1);
        RSVP testRSVP = rSVPList.get(rSVPList.size() - 1);
        assertThat(testRSVP.getRsvpResponse()).isEqualTo(DEFAULT_RSVP_RESPONSE);
    }

    @Test
    @Transactional
    public void createRSVPWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = rSVPRepository.findAll().size();

        // Create the RSVP with an existing ID
        rSVP.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRSVPMockMvc.perform(post("/api/rsvps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rSVP)))
            .andExpect(status().isBadRequest());

        // Validate the RSVP in the database
        List<RSVP> rSVPList = rSVPRepository.findAll();
        assertThat(rSVPList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkRsvpResponseIsRequired() throws Exception {
        int databaseSizeBeforeTest = rSVPRepository.findAll().size();
        // set the field null
        rSVP.setRsvpResponse(null);

        // Create the RSVP, which fails.

        restRSVPMockMvc.perform(post("/api/rsvps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rSVP)))
            .andExpect(status().isBadRequest());

        List<RSVP> rSVPList = rSVPRepository.findAll();
        assertThat(rSVPList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRSVPS() throws Exception {
        // Initialize the database
        rSVPRepository.saveAndFlush(rSVP);

        // Get all the rSVPList
        restRSVPMockMvc.perform(get("/api/rsvps?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rSVP.getId().intValue())))
            .andExpect(jsonPath("$.[*].rsvpResponse").value(hasItem(DEFAULT_RSVP_RESPONSE.toString())));
    }
    

    @Test
    @Transactional
    public void getRSVP() throws Exception {
        // Initialize the database
        rSVPRepository.saveAndFlush(rSVP);

        // Get the rSVP
        restRSVPMockMvc.perform(get("/api/rsvps/{id}", rSVP.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(rSVP.getId().intValue()))
            .andExpect(jsonPath("$.rsvpResponse").value(DEFAULT_RSVP_RESPONSE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingRSVP() throws Exception {
        // Get the rSVP
        restRSVPMockMvc.perform(get("/api/rsvps/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRSVP() throws Exception {
        // Initialize the database
        rSVPRepository.saveAndFlush(rSVP);

        int databaseSizeBeforeUpdate = rSVPRepository.findAll().size();

        // Update the rSVP
        RSVP updatedRSVP = rSVPRepository.findById(rSVP.getId()).get();
        // Disconnect from session so that the updates on updatedRSVP are not directly saved in db
        em.detach(updatedRSVP);
        updatedRSVP
            .rsvpResponse(UPDATED_RSVP_RESPONSE);

        restRSVPMockMvc.perform(put("/api/rsvps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRSVP)))
            .andExpect(status().isOk());

        // Validate the RSVP in the database
        List<RSVP> rSVPList = rSVPRepository.findAll();
        assertThat(rSVPList).hasSize(databaseSizeBeforeUpdate);
        RSVP testRSVP = rSVPList.get(rSVPList.size() - 1);
        assertThat(testRSVP.getRsvpResponse()).isEqualTo(UPDATED_RSVP_RESPONSE);
    }

    @Test
    @Transactional
    public void updateNonExistingRSVP() throws Exception {
        int databaseSizeBeforeUpdate = rSVPRepository.findAll().size();

        // Create the RSVP

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRSVPMockMvc.perform(put("/api/rsvps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rSVP)))
            .andExpect(status().isBadRequest());

        // Validate the RSVP in the database
        List<RSVP> rSVPList = rSVPRepository.findAll();
        assertThat(rSVPList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRSVP() throws Exception {
        // Initialize the database
        rSVPRepository.saveAndFlush(rSVP);

        int databaseSizeBeforeDelete = rSVPRepository.findAll().size();

        // Get the rSVP
        restRSVPMockMvc.perform(delete("/api/rsvps/{id}", rSVP.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RSVP> rSVPList = rSVPRepository.findAll();
        assertThat(rSVPList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RSVP.class);
        RSVP rSVP1 = new RSVP();
        rSVP1.setId(1L);
        RSVP rSVP2 = new RSVP();
        rSVP2.setId(rSVP1.getId());
        assertThat(rSVP1).isEqualTo(rSVP2);
        rSVP2.setId(2L);
        assertThat(rSVP1).isNotEqualTo(rSVP2);
        rSVP1.setId(null);
        assertThat(rSVP1).isNotEqualTo(rSVP2);
    }
}
