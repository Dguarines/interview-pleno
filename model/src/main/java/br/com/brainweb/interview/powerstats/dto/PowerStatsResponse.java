package br.com.brainweb.interview.powerstats.dto;

import java.util.Date;
import java.util.UUID;

import lombok.Data;

@Data
public class PowerStatsResponse {
	
    protected UUID id;

    private Integer strength;

    private Integer agility;

    private Integer dexterity;

    private Integer intelligence;

    private Date createdAt;

    private Date updatedAt;
}
