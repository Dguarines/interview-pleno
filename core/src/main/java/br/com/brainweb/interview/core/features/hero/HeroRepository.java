package br.com.brainweb.interview.core.features.hero;

import br.com.brainweb.interview.model.Hero;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface HeroRepository extends CrudRepository<Hero, UUID> {

    @Query("SELECT * FROM hero WHERE lower(name) ILIKE :name")
    List<Hero> findByName(String name);

    @Query("SELECT * FROM hero WHERE name = :name")
    Optional<Hero> getByName(String name);
}
