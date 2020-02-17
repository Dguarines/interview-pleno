package br.com.brainweb.interview.core.features.powerstats;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import br.com.brainweb.interview.hero.dto.DifferenceBetweenTwoHeroes;
import br.com.brainweb.interview.model.Hero;
import br.com.brainweb.interview.model.PowerStats;

@Service
public class PowerStatsService {
	
	private PowerStatsRepository repository;
	
	public PowerStatsService() {
	}
	
	public PowerStatsService(PowerStatsRepository repository) {
		this.repository = repository;
	}
	
	public Optional<PowerStats> findById(UUID id) {
        return this.repository.findById(id);
    }
	
	
    public DifferenceBetweenTwoHeroes compare(Hero firstHero, Hero secondHero) {
        
		PowerStats firstHeroPowerStats = repository.findById(firstHero.getPowerStatsId()).orElse(null);
        PowerStats secondHeroPowerStats = repository.findById(secondHero.getPowerStatsId()).orElse(null);

		DifferenceBetweenTwoHeroes diff = compareHeroes(firstHero.getId(), secondHero.getId(), firstHeroPowerStats, secondHeroPowerStats);

        return diff;
    }

    @SuppressWarnings("static-access")
	public DifferenceBetweenTwoHeroes compareHeroes(UUID firstHero, UUID secondHero, PowerStats firstHeroPowerStats, PowerStats secondHeroPowerStats) {
		
		DifferenceBetweenTwoHeroes diff = new DifferenceBetweenTwoHeroes().builder()
																		  .firstHero(firstHero)
																		  .secondHero(secondHero)
																		  .build();

        if (isNotEmpty(firstHeroPowerStats, secondHeroPowerStats)) {
        	
            diff.setAgility(firstHeroPowerStats.getAgility() - secondHeroPowerStats.getAgility());
            diff.setDexterity(firstHeroPowerStats.getDexterity() - secondHeroPowerStats.getDexterity());
            diff.setIntelligence(firstHeroPowerStats.getIntelligence() - secondHeroPowerStats.getIntelligence());
            diff.setStrength(firstHeroPowerStats.getStrength() - secondHeroPowerStats.getStrength());
        }
        
		return diff;
	}

	private boolean isNotEmpty(PowerStats firstHeroPowerStats, PowerStats secondHeroPowerStats) {
		return firstHeroPowerStats != null && secondHeroPowerStats != null;
	}
}
