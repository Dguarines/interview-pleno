package br.com.brainweb.interview.powerstats.dto;

import java.util.UUID;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class PowerStatsRequest {
	
    protected UUID id;

    @NotNull
    @Max(32767)
    private Integer strength;

    @NotNull
    @Max(32767)
    private Integer agility;

    @NotNull
    @Max(32767)
    private Integer dexterity;

    @NotNull
    @Max(32767)
    private Integer intelligence;
}
