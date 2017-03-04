package com.tour.of.heroes.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tour.of.heroes.HeroesRepository;
import com.tour.of.heroes.entity.Hero;
import com.tour.of.heroes.service.jsonp.JsonP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by matthias on 02/03/2017.
 */
@Path("/heroes")
@Component
public class Heroes {

    private static final ObjectMapper mapper = new ObjectMapper();

    private final HeroesRepository heroesRepository;

    @Autowired
    public Heroes(HeroesRepository heroesRepository) {
        this.heroesRepository = heroesRepository;
    }

    // Jersey's internal @JSONP doesn't work with Spring Boot
    // https://github.com/spring-projects/spring-boot/issues/2486
    @GET
    @Path("/search/{partial}")
    @Produces(MediaType.APPLICATION_JSON)
    @JsonP
    public String findByPartialName(@PathParam("partial") String partialName) throws JsonProcessingException {
        List<Hero> heroes = heroesRepository.findByNameContainingOrderByName(partialName);
        return mapper.writeValueAsString(heroes);
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @JsonP
    public String findById(@PathParam("id") String id) throws JsonProcessingException {
        Hero hero = heroesRepository.findById(Long.valueOf(id));
        return mapper.writeValueAsString(hero);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @JsonP
    public String findAll() throws JsonProcessingException {
        List<Hero> heroes = heroesRepository.findAllByOrderByName();
        return mapper.writeValueAsString(heroes);
    }

    @GET
    @Path("/create")
    @Produces(MediaType.APPLICATION_JSON)
    @JsonP
    public String create(@QueryParam("name") String name) throws JsonProcessingException {
        if (name != null) {
            Hero hero = new Hero(name);
            heroesRepository.save(hero);
            return mapper.writeValueAsString(hero);
        }
        return null;
    }

    @GET
    @Path("/save/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @JsonP
    public String save(@PathParam("id") String id,
                       @QueryParam("name") String name) throws JsonProcessingException {
        if (name != null && id != null) {
            Long idLong = Long.valueOf(id);
            Hero hero = heroesRepository.findById(idLong);
            hero.setName(name);
            heroesRepository.save(hero);
            return mapper.writeValueAsString(hero);
        }
        throw new IllegalStateException("NOT_FOUND");
    }

    @GET
    @Path("/delete/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @JsonP
    public String delete(@PathParam("id") String id) throws JsonProcessingException {
        if (id != null) {
            Long idLong = Long.valueOf(id);
            if (heroesRepository.findById(idLong) == null)
                throw new IllegalStateException("NOT_FOUND");
            heroesRepository.delete(idLong);
            return mapper.writeValueAsString(id);
        }
        throw new IllegalStateException("NOT_FOUND");
    }
}
