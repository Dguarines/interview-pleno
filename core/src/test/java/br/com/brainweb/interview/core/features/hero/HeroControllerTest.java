package br.com.brainweb.interview.core.features.hero;

import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import br.com.brainweb.interview.core.features.powerstats.PowerStatsService;
import br.com.brainweb.interview.hero.dto.DifferenceBetweenTwoHeroes;
import br.com.brainweb.interview.model.Hero;
import br.com.brainweb.interview.model.PowerStats;

@WebAppConfiguration
@SpringBootTest(classes = {HeroController.class})
@RunWith(SpringRunner.class)
public class HeroControllerTest {
	
    @MockBean
    private HeroService heroService;
    @MockBean
    private PowerStatsService powerStatsService;
    
    @InjectMocks
    private PowerStatsService powerService;
    
    @InjectMocks
    private HeroController heroController;
    
    private MockMvc mock;

    @Before
    public void setUp() {
    	
        MockitoAnnotations.initMocks(this);
        this.powerStatsService = new PowerStatsService();
        this.heroController = new HeroController(heroService, powerStatsService);
        this.mock = MockMvcBuilders.standaloneSetup(heroController).build();
    }
    
	@Test
    public void comparingPowerBetweenHeroes_mustCalculateCorrectly() {
    	
    	UUID firstHeroID = UUID.randomUUID();
    	UUID secondHeroID = UUID.randomUUID();
    	
    	PowerStats firstHeroPower = createFirstHero();
    	PowerStats secondHeroPower = createSecondHero();
    	
    	DifferenceBetweenTwoHeroes expected = createExpectedResult(firstHeroID, secondHeroID);
    	DifferenceBetweenTwoHeroes result = this.powerService.compareHeroes(firstHeroID, secondHeroID, firstHeroPower, secondHeroPower);
    	
    	Assert.assertEquals(result, expected);
    }
    
    @Test
    public void caseNameIsNull_thenBringAllHeroes() throws Exception {
    	
    	UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();

        List<Hero> heroes = Arrays.asList(createBasicHero(id1), createBasicHero(id2));

        when(heroService.find(null)).thenReturn(heroes);

        mock.perform(MockMvcRequestBuilders.get("/api/v1/heroes"))
            .andExpect(MockMvcResultMatchers.status().is(200))
            .andExpect(MockMvcResultMatchers.jsonPath("$.[0].id").value(id1.toString()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.[1].id").value(id2.toString()));
    }

	@SuppressWarnings("static-access")
	private Hero createBasicHero(UUID id) {
		return new Hero().builder().id(id).build();
	}
	
	@SuppressWarnings("static-access")
	private DifferenceBetweenTwoHeroes createExpectedResult(UUID firstHero, UUID secondHero) {
		return new DifferenceBetweenTwoHeroes().builder()
												.firstHero(firstHero)
												.secondHero(secondHero)
								    			.strength(10)
												.agility(-10)
												.dexterity(10)
												.intelligence(15)
												.build();
	}

	@SuppressWarnings("static-access")
	private PowerStats createSecondHero() {
		PowerStats secondHeroPower = new PowerStats().builder()
													 .strength(10)
													 .agility(40)
													 .dexterity(10)
													 .intelligence(5)
													 .build();
		return secondHeroPower;
	}

	@SuppressWarnings("static-access")
	private PowerStats createFirstHero() {
		PowerStats firstHeroPower = new PowerStats().builder()
    												.strength(20)
    												.agility(30)
    												.dexterity(20)
    												.intelligence(20)
    												.build();
		return firstHeroPower;
	}
}
