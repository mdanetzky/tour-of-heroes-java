package com.tour.of.heroes;

import com.tour.of.heroes.entity.Hero;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Created by matthias on 02/03/2017.
 */
@SpringBootApplication
public class HeroesServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(HeroesServiceApplication.class);
    }

    @Bean
    public CommandLineRunner demo(HeroesRepository heroesRepository) {
        return (args) -> {
            // save a couple of heroes
            heroesRepository.save(new Hero("Mr. Nice"));
            heroesRepository.save(new Hero("Narco"));
            heroesRepository.save(new Hero("Bombasto"));
            heroesRepository.save(new Hero("Celeritas"));
            heroesRepository.save(new Hero("Magneta"));
            heroesRepository.save(new Hero("RubberMan"));
            heroesRepository.save(new Hero("Dynama"));
            heroesRepository.save(new Hero("Dr IQ"));
            heroesRepository.save(new Hero("Magma"));
            heroesRepository.save(new Hero("Tornado"));
        };
    }
}
