package com.tour.of.heroes;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

/**
 * Created by matthias on 02/03/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class HeroesRepositoryTest {

    @Autowired
    private HeroesRepository heroesRepository;

    @Test
    public void heroPartialSearch() {
        Assert.state(heroesRepository.findByNameContainingOrderByName("b").size() == 2);
    }

    @Test
    public void heroSearchByName() {
        Assert.notNull(heroesRepository.findByName("Mr. Nice"));
    }
}
