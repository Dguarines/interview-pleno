package br.com.brainweb.interview.core.features.hero;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import br.com.brainweb.interview.core.converters.DozerConverter;
import br.com.brainweb.interview.core.features.powerstats.PowerStatsRepository;
import br.com.brainweb.interview.hero.dto.HeroRequest;
import br.com.brainweb.interview.hero.dto.HeroResponse;
import br.com.brainweb.interview.model.Hero;
import br.com.brainweb.interview.model.PowerStats;

@Service
public class HeroService {

    private HeroRepository heroRepository;
    private PowerStatsRepository powerRepository;

    public HeroService(HeroRepository repository, PowerStatsRepository powerRepository) {
        this.heroRepository = repository;
        this.powerRepository = powerRepository;
    }

    @Transactional
    public Hero create(HeroRequest heroRequest){
    	
    	PowerStats powerStats = savePowerStats(heroRequest);
    	Hero hero = saveHero(heroRequest, powerStats);
    	
        return hero;
    }

	private Hero saveHero(HeroRequest heroRequest, PowerStats powerStats) {
		Hero hero = DozerConverter.parseObject(heroRequest, Hero.class);
    	hero.setPowerStatsId(powerStats.getId());
    	hero.setCreatedAt(new Date());
    	hero.setUpdatedAt(new Date());
    	hero = heroRepository.save(hero);
		return hero;
	}

	private PowerStats savePowerStats(HeroRequest heroRequest) {
		PowerStats powerStats = DozerConverter.parseObject(heroRequest.getPowerStats(), PowerStats.class);
    	powerStats.setCreatedAt(new Date());
    	powerStats.setUpdatedAt(new Date());
    	powerStats = powerRepository.save(powerStats);
		return powerStats;
	}

    public Hero findById(UUID id) {
        return heroRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public List<Hero> find(String name) {

        List<Hero> heroes = new ArrayList<>();

        if(isNotEmpty(name)){
            heroes = this.heroRepository.findByName(name);
        }else {
            this.heroRepository.findAll().forEach(heroes::add);
        }

        return heroes;
    }

    @Transactional
    public HeroResponse update(UUID id, HeroRequest heroRequest) {

        validate(id, heroRequest);
        
        heroRequest.setId(id);
        
        Hero heroOriginal = findById(heroRequest.getId());
        Hero hero = updateHero(heroRequest, heroOriginal);
        updatePower(heroRequest);

        return DozerConverter.parseObject(hero, HeroResponse.class);
    }

	private PowerStats updatePower(HeroRequest heroRequest) {
		
		PowerStats powerStats = DozerConverter.parseObject(heroRequest.getPowerStats(), PowerStats.class);
        PowerStats powerOriginal = powerRepository.findById(powerStats.getId()).get();
        powerStats.setUpdatedAt(new Date());
        powerStats.setCreatedAt(powerOriginal.getCreatedAt());
        powerRepository.save(powerStats);
        return powerStats;
	}

	private Hero updateHero(HeroRequest heroRequest, Hero heroOriginal) {
		
        Hero hero = DozerConverter.parseObject(heroRequest, Hero.class);
        hero.setPowerStatsId(heroRequest.getPowerStats().getId());
        hero.setUpdatedAt(new Date());
        hero.setCreatedAt(heroOriginal.getCreatedAt());
        
        heroRepository.save(hero);
		return hero;
	}

	@Transactional
    public void delete(UUID id){
    	
    	Hero heroOriginal = findById(id);
    	PowerStats powerOriginal = powerRepository.findById(heroOriginal.getPowerStatsId()).get();
    	
        heroRepository.delete(heroOriginal);
        powerRepository.delete(powerOriginal);
    }

    private void validate(UUID id, HeroRequest heroRequest) {

        final Optional<Hero> heroFound = heroRepository.getByName(heroRequest.getName())
                                                   .filter(hero -> !hero.getId().equals(id));

        if (heroFound.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Sorry but there's already a hero with this name");
        }
    }

    private boolean isNotEmpty(String value) {
        return value != null;
    }
}
