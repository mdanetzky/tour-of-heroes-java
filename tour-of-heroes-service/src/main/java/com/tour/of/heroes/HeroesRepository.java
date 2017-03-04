package com.tour.of.heroes;

import com.tour.of.heroes.entity.Hero;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by matthias on 02/03/2017.
 */
@Repository
public interface HeroesRepository extends CrudRepository<Hero, Long> {

    List<Hero> findAll();

    List<Hero> findAllByOrderByName();

    Hero findById(Long id);

    Hero findByName(String name);

    List<Hero> findByNameContainingOrderByName(String partialName);
}