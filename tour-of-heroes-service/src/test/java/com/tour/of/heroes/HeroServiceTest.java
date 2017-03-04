package com.tour.of.heroes;

import com.tour.of.heroes.entity.Hero;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.when;
import static org.junit.Assert.*;

/**
 * Created by matthias on 02/03/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HeroServiceTest {

    @Autowired
    private HeroesRepository heroesRepository;

    @LocalServerPort
    private int port;
    private Response allHeroes;

    @Before
    public void readAllHeroes() {
        allHeroes = when().
                get(getServerUrl()).
                then().
                contentType(ContentType.JSON).
                extract().response();
    }

    private String getServerUrl() {
        return "http://localhost:" + port + "/heroes";
    }

    @Test
    public void contextLoads() {
        get(getServerUrl()).then().assertThat().statusCode(200);
    }

    @Test
    public void thereAre10Heroes() {
        Response response = get(getServerUrl())
                .then().contentType(ContentType.JSON).extract().response();
        int sizeOfList = response.body().path("size()");
        assertEquals(sizeOfList, 10);
    }

    @Test
    public void allHeroesHaveNames() throws JSONException {
        List<String> heroesNames = allHeroes.path("name");
        for (String heroName : heroesNames) {
            assertThat(heroName, Matchers.not(Matchers.isEmptyOrNullString()));
        }
    }

    @Test
    public void allHeroesHaveIds() throws JSONException {
        List<Integer> heroIds = allHeroes.path("id");
        for (Integer heroId : heroIds) {
            assertNotNull(heroId);
            assertTrue(heroId > 0);
        }
    }

    @Test
    public void deleteNonexistentIdThrows404() throws JSONException {
        get(getServerUrl() + "/delete/-1").then().assertThat().statusCode(404);
    }

    @Test
    public void jsonpReturnsCallback() throws JSONException {
        String response = get(getServerUrl() + "/?callback=testCallback").asString();
        assertTrue(response.startsWith("testCallback("));
        assertTrue(response.endsWith(");"));
    }

    @Test
    public void deleteAndRestoreHero() throws JSONException {
        Hero heroToRestore = heroesRepository.findByName("Mr. Nice");
        get(getServerUrl() + "/delete/" + heroToRestore.getId()).then().assertThat().statusCode(200);
        assertNull(heroesRepository.findByName("Mr. Nice"));
        heroesRepository.save(heroToRestore);
        assertNotNull(heroesRepository.findByName("Mr. Nice"));
    }

    @Test
    public void searchForBreturns2Records() throws JSONException {
        int recordsFound = get(getServerUrl() + "/search/b").then().contentType(ContentType.JSON).
                extract().response().body().path("size()");
        assertEquals(recordsFound, 2);
    }

    @Test
    public void updateAndRestoreHero() throws JSONException {
        Hero heroToRestore = heroesRepository.findByName("Mr. Nice");
        get(getServerUrl() + "/save/" + heroToRestore.getId() + "?name=test").then().assertThat().statusCode(200);
        assertNull(heroesRepository.findByName("Mr. Nice"));
        assertNotNull(heroesRepository.findByName("test"));
        heroesRepository.save(heroToRestore);
        assertNull(heroesRepository.findByName("test"));
        assertNotNull(heroesRepository.findByName("Mr. Nice"));
    }
}
