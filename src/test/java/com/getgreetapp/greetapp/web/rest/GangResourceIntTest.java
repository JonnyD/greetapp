package com.getgreetapp.greetapp.web.rest;

import com.getgreetapp.greetapp.GreetappApp;

import com.getgreetapp.greetapp.domain.Gang;
import com.getgreetapp.greetapp.repository.GangRepository;
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
import java.math.BigDecimal;
import java.util.List;


import static com.getgreetapp.greetapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the GangResource REST controller.
 *
 * @see GangResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GreetappApp.class)
public class GangResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_LONGITUDE = new BigDecimal(1);
    private static final BigDecimal UPDATED_LONGITUDE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_LATITUDE = new BigDecimal(1);
    private static final BigDecimal UPDATED_LATITUDE = new BigDecimal(2);

    private static final String DEFAULT_MEMBERSHIP_APPROVAL = "AAAAAAAAAA";
    private static final String UPDATED_MEMBERSHIP_APPROVAL = "BBBBBBBBBB";

    private static final String DEFAULT_PRIVACY = "AAAAAAAAAA";
    private static final String UPDATED_PRIVACY = "BBBBBBBBBB";

    @Autowired
    private GangRepository gangRepository;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restGangMockMvc;

    private Gang gang;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GangResource gangResource = new GangResource(gangRepository);
        this.restGangMockMvc = MockMvcBuilders.standaloneSetup(gangResource)
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
    public static Gang createEntity(EntityManager em) {
        Gang gang = new Gang()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .longitude(DEFAULT_LONGITUDE)
            .latitude(DEFAULT_LATITUDE)
            .membershipApproval(DEFAULT_MEMBERSHIP_APPROVAL)
            .privacy(DEFAULT_PRIVACY);
        return gang;
    }

    @Before
    public void initTest() {
        gang = createEntity(em);
    }

    @Test
    @Transactional
    public void createGang() throws Exception {
        int databaseSizeBeforeCreate = gangRepository.findAll().size();

        // Create the Gang
        restGangMockMvc.perform(post("/api/gangs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gang)))
            .andExpect(status().isCreated());

        // Validate the Gang in the database
        List<Gang> gangList = gangRepository.findAll();
        assertThat(gangList).hasSize(databaseSizeBeforeCreate + 1);
        Gang testGang = gangList.get(gangList.size() - 1);
        assertThat(testGang.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testGang.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testGang.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testGang.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testGang.getMembershipApproval()).isEqualTo(DEFAULT_MEMBERSHIP_APPROVAL);
        assertThat(testGang.getPrivacy()).isEqualTo(DEFAULT_PRIVACY);
    }

    @Test
    @Transactional
    public void createGangWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = gangRepository.findAll().size();

        // Create the Gang with an existing ID
        gang.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGangMockMvc.perform(post("/api/gangs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gang)))
            .andExpect(status().isBadRequest());

        // Validate the Gang in the database
        List<Gang> gangList = gangRepository.findAll();
        assertThat(gangList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = gangRepository.findAll().size();
        // set the field null
        gang.setName(null);

        // Create the Gang, which fails.

        restGangMockMvc.perform(post("/api/gangs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gang)))
            .andExpect(status().isBadRequest());

        List<Gang> gangList = gangRepository.findAll();
        assertThat(gangList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = gangRepository.findAll().size();
        // set the field null
        gang.setDescription(null);

        // Create the Gang, which fails.

        restGangMockMvc.perform(post("/api/gangs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gang)))
            .andExpect(status().isBadRequest());

        List<Gang> gangList = gangRepository.findAll();
        assertThat(gangList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLongitudeIsRequired() throws Exception {
        int databaseSizeBeforeTest = gangRepository.findAll().size();
        // set the field null
        gang.setLongitude(null);

        // Create the Gang, which fails.

        restGangMockMvc.perform(post("/api/gangs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gang)))
            .andExpect(status().isBadRequest());

        List<Gang> gangList = gangRepository.findAll();
        assertThat(gangList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLatitudeIsRequired() throws Exception {
        int databaseSizeBeforeTest = gangRepository.findAll().size();
        // set the field null
        gang.setLatitude(null);

        // Create the Gang, which fails.

        restGangMockMvc.perform(post("/api/gangs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gang)))
            .andExpect(status().isBadRequest());

        List<Gang> gangList = gangRepository.findAll();
        assertThat(gangList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMembershipApprovalIsRequired() throws Exception {
        int databaseSizeBeforeTest = gangRepository.findAll().size();
        // set the field null
        gang.setMembershipApproval(null);

        // Create the Gang, which fails.

        restGangMockMvc.perform(post("/api/gangs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gang)))
            .andExpect(status().isBadRequest());

        List<Gang> gangList = gangRepository.findAll();
        assertThat(gangList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrivacyIsRequired() throws Exception {
        int databaseSizeBeforeTest = gangRepository.findAll().size();
        // set the field null
        gang.setPrivacy(null);

        // Create the Gang, which fails.

        restGangMockMvc.perform(post("/api/gangs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gang)))
            .andExpect(status().isBadRequest());

        List<Gang> gangList = gangRepository.findAll();
        assertThat(gangList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGangs() throws Exception {
        // Initialize the database
        gangRepository.saveAndFlush(gang);

        // Get all the gangList
        restGangMockMvc.perform(get("/api/gangs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gang.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.intValue())))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.intValue())))
            .andExpect(jsonPath("$.[*].membershipApproval").value(hasItem(DEFAULT_MEMBERSHIP_APPROVAL.toString())))
            .andExpect(jsonPath("$.[*].privacy").value(hasItem(DEFAULT_PRIVACY.toString())));
    }
    

    @Test
    @Transactional
    public void getGang() throws Exception {
        // Initialize the database
        gangRepository.saveAndFlush(gang);

        // Get the gang
        restGangMockMvc.perform(get("/api/gangs/{id}", gang.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(gang.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE.intValue()))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE.intValue()))
            .andExpect(jsonPath("$.membershipApproval").value(DEFAULT_MEMBERSHIP_APPROVAL.toString()))
            .andExpect(jsonPath("$.privacy").value(DEFAULT_PRIVACY.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingGang() throws Exception {
        // Get the gang
        restGangMockMvc.perform(get("/api/gangs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGang() throws Exception {
        // Initialize the database
        gangRepository.saveAndFlush(gang);

        int databaseSizeBeforeUpdate = gangRepository.findAll().size();

        // Update the gang
        Gang updatedGang = gangRepository.findById(gang.getId()).get();
        // Disconnect from session so that the updates on updatedGang are not directly saved in db
        em.detach(updatedGang);
        updatedGang
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .longitude(UPDATED_LONGITUDE)
            .latitude(UPDATED_LATITUDE)
            .membershipApproval(UPDATED_MEMBERSHIP_APPROVAL)
            .privacy(UPDATED_PRIVACY);

        restGangMockMvc.perform(put("/api/gangs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedGang)))
            .andExpect(status().isOk());

        // Validate the Gang in the database
        List<Gang> gangList = gangRepository.findAll();
        assertThat(gangList).hasSize(databaseSizeBeforeUpdate);
        Gang testGang = gangList.get(gangList.size() - 1);
        assertThat(testGang.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGang.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testGang.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testGang.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testGang.getMembershipApproval()).isEqualTo(UPDATED_MEMBERSHIP_APPROVAL);
        assertThat(testGang.getPrivacy()).isEqualTo(UPDATED_PRIVACY);
    }

    @Test
    @Transactional
    public void updateNonExistingGang() throws Exception {
        int databaseSizeBeforeUpdate = gangRepository.findAll().size();

        // Create the Gang

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restGangMockMvc.perform(put("/api/gangs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gang)))
            .andExpect(status().isBadRequest());

        // Validate the Gang in the database
        List<Gang> gangList = gangRepository.findAll();
        assertThat(gangList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGang() throws Exception {
        // Initialize the database
        gangRepository.saveAndFlush(gang);

        int databaseSizeBeforeDelete = gangRepository.findAll().size();

        // Get the gang
        restGangMockMvc.perform(delete("/api/gangs/{id}", gang.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Gang> gangList = gangRepository.findAll();
        assertThat(gangList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Gang.class);
        Gang gang1 = new Gang();
        gang1.setId(1L);
        Gang gang2 = new Gang();
        gang2.setId(gang1.getId());
        assertThat(gang1).isEqualTo(gang2);
        gang2.setId(2L);
        assertThat(gang1).isNotEqualTo(gang2);
        gang1.setId(null);
        assertThat(gang1).isNotEqualTo(gang2);
    }
}
