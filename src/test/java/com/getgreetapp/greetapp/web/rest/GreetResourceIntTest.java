package com.getgreetapp.greetapp.web.rest;

import com.getgreetapp.greetapp.GreetappApp;

import com.getgreetapp.greetapp.domain.Greet;
import com.getgreetapp.greetapp.repository.GreetRepository;
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
 * Test class for the GreetResource REST controller.
 *
 * @see GreetResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GreetappApp.class)
public class GreetResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_LONGITUDE = new BigDecimal(1);
    private static final BigDecimal UPDATED_LONGITUDE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_LATITUDE = new BigDecimal(1);
    private static final BigDecimal UPDATED_LATITUDE = new BigDecimal(2);

    private static final String DEFAULT_PRIVACY = "AAAAAAAAAA";
    private static final String UPDATED_PRIVACY = "BBBBBBBBBB";

    @Autowired
    private GreetRepository greetRepository;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restGreetMockMvc;

    private Greet greet;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GreetResource greetResource = new GreetResource(greetRepository);
        this.restGreetMockMvc = MockMvcBuilders.standaloneSetup(greetResource)
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
    public static Greet createEntity(EntityManager em) {
        Greet greet = new Greet()
            .title(DEFAULT_TITLE)
            .description(DEFAULT_DESCRIPTION)
            .longitude(DEFAULT_LONGITUDE)
            .latitude(DEFAULT_LATITUDE)
            .privacy(DEFAULT_PRIVACY);
        return greet;
    }

    @Before
    public void initTest() {
        greet = createEntity(em);
    }

    @Test
    @Transactional
    public void createGreet() throws Exception {
        int databaseSizeBeforeCreate = greetRepository.findAll().size();

        // Create the Greet
        restGreetMockMvc.perform(post("/api/greets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(greet)))
            .andExpect(status().isCreated());

        // Validate the Greet in the database
        List<Greet> greetList = greetRepository.findAll();
        assertThat(greetList).hasSize(databaseSizeBeforeCreate + 1);
        Greet testGreet = greetList.get(greetList.size() - 1);
        assertThat(testGreet.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testGreet.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testGreet.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testGreet.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testGreet.getPrivacy()).isEqualTo(DEFAULT_PRIVACY);
    }

    @Test
    @Transactional
    public void createGreetWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = greetRepository.findAll().size();

        // Create the Greet with an existing ID
        greet.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGreetMockMvc.perform(post("/api/greets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(greet)))
            .andExpect(status().isBadRequest());

        // Validate the Greet in the database
        List<Greet> greetList = greetRepository.findAll();
        assertThat(greetList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = greetRepository.findAll().size();
        // set the field null
        greet.setTitle(null);

        // Create the Greet, which fails.

        restGreetMockMvc.perform(post("/api/greets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(greet)))
            .andExpect(status().isBadRequest());

        List<Greet> greetList = greetRepository.findAll();
        assertThat(greetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrivacyIsRequired() throws Exception {
        int databaseSizeBeforeTest = greetRepository.findAll().size();
        // set the field null
        greet.setPrivacy(null);

        // Create the Greet, which fails.

        restGreetMockMvc.perform(post("/api/greets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(greet)))
            .andExpect(status().isBadRequest());

        List<Greet> greetList = greetRepository.findAll();
        assertThat(greetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGreets() throws Exception {
        // Initialize the database
        greetRepository.saveAndFlush(greet);

        // Get all the greetList
        restGreetMockMvc.perform(get("/api/greets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(greet.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.intValue())))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.intValue())))
            .andExpect(jsonPath("$.[*].privacy").value(hasItem(DEFAULT_PRIVACY.toString())));
    }
    

    @Test
    @Transactional
    public void getGreet() throws Exception {
        // Initialize the database
        greetRepository.saveAndFlush(greet);

        // Get the greet
        restGreetMockMvc.perform(get("/api/greets/{id}", greet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(greet.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE.intValue()))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE.intValue()))
            .andExpect(jsonPath("$.privacy").value(DEFAULT_PRIVACY.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingGreet() throws Exception {
        // Get the greet
        restGreetMockMvc.perform(get("/api/greets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGreet() throws Exception {
        // Initialize the database
        greetRepository.saveAndFlush(greet);

        int databaseSizeBeforeUpdate = greetRepository.findAll().size();

        // Update the greet
        Greet updatedGreet = greetRepository.findById(greet.getId()).get();
        // Disconnect from session so that the updates on updatedGreet are not directly saved in db
        em.detach(updatedGreet);
        updatedGreet
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .longitude(UPDATED_LONGITUDE)
            .latitude(UPDATED_LATITUDE)
            .privacy(UPDATED_PRIVACY);

        restGreetMockMvc.perform(put("/api/greets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedGreet)))
            .andExpect(status().isOk());

        // Validate the Greet in the database
        List<Greet> greetList = greetRepository.findAll();
        assertThat(greetList).hasSize(databaseSizeBeforeUpdate);
        Greet testGreet = greetList.get(greetList.size() - 1);
        assertThat(testGreet.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testGreet.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testGreet.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testGreet.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testGreet.getPrivacy()).isEqualTo(UPDATED_PRIVACY);
    }

    @Test
    @Transactional
    public void updateNonExistingGreet() throws Exception {
        int databaseSizeBeforeUpdate = greetRepository.findAll().size();

        // Create the Greet

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restGreetMockMvc.perform(put("/api/greets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(greet)))
            .andExpect(status().isBadRequest());

        // Validate the Greet in the database
        List<Greet> greetList = greetRepository.findAll();
        assertThat(greetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGreet() throws Exception {
        // Initialize the database
        greetRepository.saveAndFlush(greet);

        int databaseSizeBeforeDelete = greetRepository.findAll().size();

        // Get the greet
        restGreetMockMvc.perform(delete("/api/greets/{id}", greet.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Greet> greetList = greetRepository.findAll();
        assertThat(greetList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Greet.class);
        Greet greet1 = new Greet();
        greet1.setId(1L);
        Greet greet2 = new Greet();
        greet2.setId(greet1.getId());
        assertThat(greet1).isEqualTo(greet2);
        greet2.setId(2L);
        assertThat(greet1).isNotEqualTo(greet2);
        greet1.setId(null);
        assertThat(greet1).isNotEqualTo(greet2);
    }
}
