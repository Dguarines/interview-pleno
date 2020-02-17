package br.com.brainweb.interview.core.features.hero;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.brainweb.interview.core.converters.DozerConverter;
import br.com.brainweb.interview.core.features.powerstats.PowerStatsService;
import br.com.brainweb.interview.hero.dto.DifferenceBetweenTwoHeroes;
import br.com.brainweb.interview.hero.dto.HeroRequest;
import br.com.brainweb.interview.hero.dto.HeroResponse;
import br.com.brainweb.interview.model.Hero;
import br.com.brainweb.interview.model.PowerStats;

@RestController
@RequestMapping("/api/v1/heroes")
public class HeroController {

    private HeroService heroService;
    private PowerStatsService powerService;

    public HeroController(HeroService heroService, PowerStatsService powerService) {
        this.heroService = heroService;
        this.powerService = powerService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<HeroResponse> findById(@PathVariable("id") String id){
    	    	
    	Hero heroFound = heroService.findById(UUID.fromString(id));
        PowerStats powerFound = powerService.findById(heroFound.getPowerStatsId()).get();
        
        HeroResponse heroResponse = DozerConverter.parseObject(heroFound, HeroResponse.class);
        heroResponse.setPowerStats(powerFound);

        return ResponseEntity.status(HttpStatus.FOUND).body(heroResponse);
    }

    @GetMapping
    public ResponseEntity<List<HeroResponse>> findByFilter(@RequestParam(value = "name", required = false) String name){

        List<HeroResponse> heroes = heroService.find(name).stream().map(hero -> DozerConverter.parseObject(hero, HeroResponse.class)).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(heroes);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<HeroResponse> update(@PathVariable("id") final String id, @RequestBody final HeroRequest heroRequest){

        HeroResponse heroUpdated = heroService.update(UUID.fromString(id), heroRequest);

        return ResponseEntity.status(HttpStatus.OK).body(heroUpdated);
    }

    @PostMapping
    public ResponseEntity<HeroResponse> create(@Valid @RequestBody final HeroRequest heroRequest) {

        HeroResponse heroCreated = DozerConverter.parseObject(heroService.create(heroRequest), HeroResponse.class) ;

        return ResponseEntity.status(HttpStatus.CREATED).body(heroCreated);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {

        heroService.delete(UUID.fromString(id));

        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/{id_hero_1}/{id_hero_2}/comparation")
    public ResponseEntity<DifferenceBetweenTwoHeroes> compare(@PathVariable("id_hero_1") String hero1, @PathVariable("id_hero_2") String hero2) {
        
    	Hero firstHero = this.heroService.findById(UUID.fromString(hero1));
        Hero secondHero = this.heroService.findById(UUID.fromString(hero2));
        
        DifferenceBetweenTwoHeroes diff = this.powerService.compare(firstHero, secondHero);
   
        return ResponseEntity.status(HttpStatus.OK).body(diff);
    }

}
