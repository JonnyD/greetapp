package com.getgreetapp.greetapp.web.rest;

import com.getgreetapp.greetapp.GreetappApp;

import com.getgreetapp.greetapp.domain.GangUser;
import com.getgreetapp.greetapp.repository.GangUserRepository;
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
 * Test class for the GangUserResource REST controller.
 *
 * @see GangUserResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GreetappApp.class)
public class GangUserResourceIntTest {

    private static final String DEFAULT_ROLE = "AAAAAAAAAA";
    private static final String UPDATED_ROLE = "BBBBBBBBBB";

    @Autowired
    private GangUserRepository gangUserRepository;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restGangUserMockMvc;

    private GangUser gangUser;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GangUserResource gangUserResource = new GangUserResource(gangUserRepository);
        this.restGangUserMockMvc = MockMvcBuilders.standaloneSetup(gangUserResource)
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
    public static GangUser createEntity(EntityManager em) {
        GangUser gangUser = new GangUser()
            .role(DEFAULT_ROLE);
        return gangUser;
    }

    @Before
    public void initTest() {
        gangUser = createEntity(em);
    }

    @Test
    @Transactional
    public void createGangUser() throws Exception {
        int databaseSizeBeforeCreate = gangUserRepository.findAll().size();

        // Create the GangUser
        restGangUserMockMvc.perform(post("/api/gang-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gangUser)))
            .andExpect(status().isCreated());

        // Validate the GangUser in the database
        List<GangUser> gangUserList = gangUserRepository.findAll();
        assertThat(gangUserList).hasSize(databaseSizeBeforeCreate + 1);
        GangUser testGangUser = gangUserList.get(gangUserList.size() - 1);
        assertThat(testGangUser.getRole()).isEqualTo(DEFAULT_ROLE);
    }

    @Test
    @Transactional
    public void createGangUserWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = gangUserRepository.findAll().size();

        // Create the GangUser with an existing ID
        gangUser.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGangUserMockMvc.perform(post("/api/gang-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gangUser)))
            .andExpect(status().isBadRequest());

        // Validate the GangUser in the database
        List<GangUser> gangUserList = gangUserRepository.findAll();
        assertThat(gangUserList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkRoleIsRequired() throws Exception {
        int databaseSizeBeforeTest = gangUserRepository.findAll().size();
        // set the field null
        gangUser.setRole(null);

        // Create the GangUser, which fails.

        restGangUserMockMvc.perform(post("/api/gang-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gangUser)))
            .andExpect(status().isBadRequest());

        List<GangUser> gangUserList = gangUserRepository.findAll();
        assertThat(gangUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGangUsers() throws Exception {
        // Initialize the database
        gangUserRepository.saveAndFlush(gangUser);

        // Get all the gangUserList
        restGangUserMockMvc.perform(get("/api/gang-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gangUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].role").value(hasItem(DEFAULT_ROLE.toString())));
    }
    

    @Test
    @Transactional
    public void getGangUser() throws Exception {
        // Initialize the database
        gangUserRepository.saveAndFlush(gangUser);

        // Get the gangUser
        restGangUserMockMvc.perform(get("/api/gang-users/{id}", gangUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(gangUser.getId().intValue()))
            .andExpect(jsonPath("$.role").value(DEFAULT_ROLE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingGangUser() throws Exception {
        // Get the gangUser
        restGangUserMockMvc.perform(get("/api/gang-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGangUser() throws Exception {
        // Initialize the database
        gangUserRepository.saveAndFlush(gangUser);

        int databaseSizeBeforeUpdate = gangUserRepository.findAll().size();

        // Update the gangUser
        GangUser updatedGangUser = gangUserRepository.findById(gangUser.getId()).get();
        // Disconnect from session so that the updates on updatedGangUser are not directly saved in db
        em.detach(updatedGangUser);
        updatedGangUser
            .role(UPDATED_ROLE);

        restGangUserMockMvc.perform(put("/api/gang-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedGangUser)))
            .andExpect(status().isOk());

        // Validate the GangUser in the database
        List<GangUser> gangUserList = gangUserRepository.findAll();
        assertThat(gangUserList).hasSize(databaseSizeBeforeUpdate);
        GangUser testGangUser = gangUserList.get(gangUserList.size() - 1);
        assertThat(testGangUser.getRole()).isEqualTo(UPDATED_ROLE);
    }

    @Test
    @Transactional
    public void updateNonExistingGangUser() throws Exception {
        int databaseSizeBeforeUpdate = gangUserRepository.findAll().size();

        // Create the GangUser

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restGangUserMockMvc.perform(put("/api/gang-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gangUser)))
            .andExpect(status().isBadRequest());

        // Validate the GangUser in the database
        List<GangUser> gangUserList = gangUserRepository.findAll();
        assertThat(gangUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGangUser() throws Exception {
        // Initialize the database
        gangUserRepository.saveAndFlush(gangUser);

        int databaseSizeBeforeDelete = gangUserRepository.findAll().size();

        // Get the gangUser
        restGangUserMockMvc.perform(delete("/api/gang-users/{id}", gangUser.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<GangUser> gangUserList = gangUserRepository.findAll();
        assertThat(gangUserList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GangUser.class);
        GangUser gangUser1 = new GangUser();
        gangUser1.setId(1L);
        GangUser gangUser2 = new GangUser();
        gangUser2.setId(gangUser1.getId());
        assertThat(gangUser1).isEqualTo(gangUser2);
        gangUser2.setId(2L);
        assertThat(gangUser1).isNotEqualTo(gangUser2);
        gangUser1.setId(null);
        assertThat(gangUser1).isNotEqualTo(gangUser2);
    }
}
