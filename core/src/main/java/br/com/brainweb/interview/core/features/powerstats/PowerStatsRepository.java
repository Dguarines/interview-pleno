package br.com.brainweb.interview.core.features.powerstats;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import br.com.brainweb.interview.model.PowerStats;

public interface PowerStatsRepository extends CrudRepository<PowerStats, UUID>{
}
