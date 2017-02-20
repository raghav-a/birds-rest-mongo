package com.birds.controller;

import com.birds.controller.data.BirdData;
import com.birds.dao.BirdsRepository;
import com.birds.model.Bird;
import com.google.common.collect.Lists;
import org.bson.types.ObjectId;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.junit.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.*;

import java.io.IOException;
import java.util.List;

import static com.birds.model.ApplicationDate.currentDate;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:test.properties")
public class BirdsRegistrySpecs {

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    private BirdsRepository birdsRegistryDao;

    @Before
    public void setUp() {
        birdsRegistryDao.deleteAll();
    }

    @After
    public void tearDown() {
        birdsRegistryDao.deleteAll();
    }

    @Test
    public void failOnPostWithInvalidBirdData() {
        final ResponseEntity response = restTemplate.postForEntity("/birds", invalidBirdData(), ResponseEntity.class);
        assertThat(response.getStatusCode(), Matchers.equalTo(HttpStatus.BAD_REQUEST));

    }

    @Test
    public void saveBirdOnPostWithCorrectBirdData() {
        final BirdData input = validBirdData();
        final BirdData bird = restTemplate.postForObject("/birds", input, BirdData.class);
        assertThat(bird, Matchers.notNullValue());
        assertThat(bird.getId(), Matchers.notNullValue());
        assertThat(fetchFromDb(bird.getId()), Matchers.equalTo(new Bird(input)));
    }

    @Test
    public void getBirdById() {
        final BirdData validBirdData = validBirdData();
        final Bird bird = new Bird(validBirdData);
        birdsRegistryDao.save(bird);
        final BirdData response = restTemplate.getForObject("/birds/{id}", BirdData.class, bird.idAsHex());
        assertThat(response, Matchers.equalTo(validBirdData));
    }

    @Test
    public void notFoundOnInvalidId() {
        final ResponseEntity response = restTemplate.getForEntity("/birds/{id}", ResponseEntity.class, "randomId");
        assertThat(response.getStatusCode(), Matchers.equalTo(HttpStatus.NOT_FOUND));
    }

    @Test
    public void getAllVisibleBirds() {
        final Bird visibleBirdOne = new Bird(validBirdData());
        final Bird visibleBirdTwo = new Bird(validBirdData());
        final Bird visibleBirdThree = new Bird(validBirdData());

        saveBirds(visibleBirdOne, visibleBirdTwo, visibleBirdThree);

        saveBirds(new Bird(invisibleValidBirdData()));
        saveBirds(new Bird(invisibleValidBirdData()));

        final List birds = restTemplate.getForObject("/birds", List.class);

        assertThat(birds.size(), Matchers.equalTo(3));
        assertTrue(birds.contains(visibleBirdOne.idAsHex()));
        assertTrue(birds.contains(visibleBirdTwo.idAsHex()));
        assertTrue(birds.contains(visibleBirdThree.idAsHex()));
    }

    @Test
    public void shouldDeleteBirdByValidId() {
        final Bird validBird = new Bird(validBirdData());
        saveBirds(validBird);
        restTemplate.delete("/birds/{id}", validBird.idAsHex());
        assertThat(fetchFromDb(validBird.id()), Matchers.nullValue());
    }

    @Test
    public void notFoundOnDeleteForInvalidId() throws IOException {
        ClientHttpResponse clientHttpResponse = restTemplate.execute("/birds/{id}", HttpMethod.DELETE, null, response -> response, "randomId");
        assertThat(clientHttpResponse.getStatusCode(), Matchers.equalTo(HttpStatus.NOT_FOUND));
    }

    private Bird fetchFromDb(String id) {
        return birdsRegistryDao.findOne(new ObjectId(id));
    }

    private Bird fetchFromDb(ObjectId id) {
        return birdsRegistryDao.findOne(id);
    }

    private void saveBirds(Bird... birds) {
        for (Bird bird : birds) {
            birdsRegistryDao.save(bird);
        }

    }

    private BirdData invalidBirdData() {
        return new BirdData(null, "birdFamily", Lists.newArrayList("asia", "north america"), currentDate(), false);
    }

    private BirdData invisibleValidBirdData() {
        return new BirdData("birdName", "birdFamily", Lists.newArrayList("asia", "north america"), currentDate(), false);
    }

    private BirdData validBirdData() {
        return new BirdData("birdName", "birdFamily", Lists.newArrayList("north america","asia"), currentDate(), true);
    }


}