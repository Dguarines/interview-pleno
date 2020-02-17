package br.com.brainweb.interview.hero.dto;

import java.util.Date;
import java.util.UUID;

import br.com.brainweb.interview.model.PowerStats;
import br.com.brainweb.interview.model.Race;
import lombok.Data;

@Data
public class HeroResponse {
	
    private UUID id;

    private String name;

    private Race race;

    private PowerStats powerStats;
    
    private Date createdAt;

    private Date updatedAt;
}
