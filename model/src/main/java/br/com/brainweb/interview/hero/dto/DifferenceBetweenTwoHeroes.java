package br.com.brainweb.interview.hero.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DifferenceBetweenTwoHeroes {
	
	@With
    private UUID firstHero;
	@With
    private UUID secondHero;
    
    private Integer strength;
    private Integer agility;
    private Integer dexterity;
    private Integer intelligence;
}
