package com.getgreetapp.greetapp.web.rest;

import com.getgreetapp.greetapp.GreetappApp;

import com.getgreetapp.greetapp.domain.GreetInvitation;
import com.getgreetapp.greetapp.repository.GreetInvitationRepository;
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
 * Test class for the GreetInvitationResource REST controller.
 *
 * @see GreetInvitationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GreetappApp.class)
public class GreetInvitationResourceIntTest {

    private static final String DEFAULT_GREET_INVITATION_RESPONSE = "AAAAAAAAAA";
    private static final String UPDATED_GREET_INVITATION_RESPONSE = "BBBBBBBBBB";

    @Autowired
    private GreetInvitationRepository greetInvitationRepository;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restGreetInvitationMockMvc;

    private GreetInvitation greetInvitation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GreetInvitationResource greetInvitationResource = new GreetInvitationResource(greetInvitationRepository);
        this.restGreetInvitationMockMvc = MockMvcBuilders.standaloneSetup(greetInvitationResource)
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
    public static GreetInvitation createEntity(EntityManager em) {
        GreetInvitation greetInvitation = new GreetInvitation()
            .greetInvitationResponse(DEFAULT_GREET_INVITATION_RESPONSE);
        return greetInvitation;
    }

    @Before
    public void initTest() {
        greetInvitation = createEntity(em);
    }

    @Test
    @Transactional
    public void createGreetInvitation() throws Exception {
        int databaseSizeBeforeCreate = greetInvitationRepository.findAll().size();

        // Create the GreetInvitation
        restGreetInvitationMockMvc.perform(post("/api/greet-invitations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(greetInvitation)))
            .andExpect(status().isCreated());

        // Validate the GreetInvitation in the database
        List<GreetInvitation> greetInvitationList = greetInvitationRepository.findAll();
        assertThat(greetInvitationList).hasSize(databaseSizeBeforeCreate + 1);
        GreetInvitation testGreetInvitation = greetInvitationList.get(greetInvitationList.size() - 1);
        assertThat(testGreetInvitation.getGreetInvitationResponse()).isEqualTo(DEFAULT_GREET_INVITATION_RESPONSE);
    }

    @Test
    @Transactional
    public void createGreetInvitationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = greetInvitationRepository.findAll().size();

        // Create the GreetInvitation with an existing ID
        greetInvitation.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGreetInvitationMockMvc.perform(post("/api/greet-invitations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(greetInvitation)))
            .andExpect(status().isBadRequest());

        // Validate the GreetInvitation in the database
        List<GreetInvitation> greetInvitationList = greetInvitationRepository.findAll();
        assertThat(greetInvitationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkGreetInvitationResponseIsRequired() throws Exception {
        int databaseSizeBeforeTest = greetInvitationRepository.findAll().size();
        // set the field null
        greetInvitation.setGreetInvitationResponse(null);

        // Create the GreetInvitation, which fails.

        restGreetInvitationMockMvc.perform(post("/api/greet-invitations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(greetInvitation)))
            .andExpect(status().isBadRequest());

        List<GreetInvitation> greetInvitationList = greetInvitationRepository.findAll();
        assertThat(greetInvitationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGreetInvitations() throws Exception {
        // Initialize the database
        greetInvitationRepository.saveAndFlush(greetInvitation);

        // Get all the greetInvitationList
        restGreetInvitationMockMvc.perform(get("/api/greet-invitations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(greetInvitation.getId().intValue())))
            .andExpect(jsonPath("$.[*].greetInvitationResponse").value(hasItem(DEFAULT_GREET_INVITATION_RESPONSE.toString())));
    }
    

    @Test
    @Transactional
    public void getGreetInvitation() throws Exception {
        // Initialize the database
        greetInvitationRepository.saveAndFlush(greetInvitation);

        // Get the greetInvitation
        restGreetInvitationMockMvc.perform(get("/api/greet-invitations/{id}", greetInvitation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(greetInvitation.getId().intValue()))
            .andExpect(jsonPath("$.greetInvitationResponse").value(DEFAULT_GREET_INVITATION_RESPONSE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingGreetInvitation() throws Exception {
        // Get the greetInvitation
        restGreetInvitationMockMvc.perform(get("/api/greet-invitations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGreetInvitation() throws Exception {
        // Initialize the database
        greetInvitationRepository.saveAndFlush(greetInvitation);

        int databaseSizeBeforeUpdate = greetInvitationRepository.findAll().size();

        // Update the greetInvitation
        GreetInvitation updatedGreetInvitation = greetInvitationRepository.findById(greetInvitation.getId()).get();
        // Disconnect from session so that the updates on updatedGreetInvitation are not directly saved in db
        em.detach(updatedGreetInvitation);
        updatedGreetInvitation
            .greetInvitationResponse(UPDATED_GREET_INVITATION_RESPONSE);

        restGreetInvitationMockMvc.perform(put("/api/greet-invitations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedGreetInvitation)))
            .andExpect(status().isOk());

        // Validate the GreetInvitation in the database
        List<GreetInvitation> greetInvitationList = greetInvitationRepository.findAll();
        assertThat(greetInvitationList).hasSize(databaseSizeBeforeUpdate);
        GreetInvitation testGreetInvitation = greetInvitationList.get(greetInvitationList.size() - 1);
        assertThat(testGreetInvitation.getGreetInvitationResponse()).isEqualTo(UPDATED_GREET_INVITATION_RESPONSE);
    }

    @Test
    @Transactional
    public void updateNonExistingGreetInvitation() throws Exception {
        int databaseSizeBeforeUpdate = greetInvitationRepository.findAll().size();

        // Create the GreetInvitation

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restGreetInvitationMockMvc.perform(put("/api/greet-invitations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(greetInvitation)))
            .andExpect(status().isBadRequest());

        // Validate the GreetInvitation in the database
        List<GreetInvitation> greetInvitationList = greetInvitationRepository.findAll();
        assertThat(greetInvitationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGreetInvitation() throws Exception {
        // Initialize the database
        greetInvitationRepository.saveAndFlush(greetInvitation);

        int databaseSizeBeforeDelete = greetInvitationRepository.findAll().size();

        // Get the greetInvitation
        restGreetInvitationMockMvc.perform(delete("/api/greet-invitations/{id}", greetInvitation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<GreetInvitation> greetInvitationList = greetInvitationRepository.findAll();
        assertThat(greetInvitationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GreetInvitation.class);
        GreetInvitation greetInvitation1 = new GreetInvitation();
        greetInvitation1.setId(1L);
        GreetInvitation greetInvitation2 = new GreetInvitation();
        greetInvitation2.setId(greetInvitation1.getId());
        assertThat(greetInvitation1).isEqualTo(greetInvitation2);
        greetInvitation2.setId(2L);
        assertThat(greetInvitation1).isNotEqualTo(greetInvitation2);
        greetInvitation1.setId(null);
        assertThat(greetInvitation1).isNotEqualTo(greetInvitation2);
    }
}
