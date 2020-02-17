package br.com.brainweb.interview.hero.dto;

import java.util.UUID;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.com.brainweb.interview.model.Race;
import br.com.brainweb.interview.powerstats.dto.PowerStatsRequest;
import lombok.Data;

@Data
public class HeroRequest {

    private UUID id;

    @NotNull(message = "Name: value must not be null.")
    @NotEmpty(message = "Name: value must not be empty.")
    @Length(max = 255, message = "Name: value must not be greater than 255.")
    private String name;

    @NotNull(message = "Race: value must not be null.")
    private Race race;

    @NotNull(message = "PowerStats: value must not be null.")
    private PowerStatsRequest powerStats;
}
