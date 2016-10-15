package com.birds.controller;

import com.birds.DateUtil;
import com.birds.dao.BirdsRepository;
import com.birds.model.Bird;
import org.bson.types.ObjectId;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.junit.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.test.context.junit4.*;

import java.io.IOException;
import java.util.List;

import static com.google.common.collect.Sets.newHashSet;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BirdsRegistryControllerTest {

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
    public void badRequestOnPostWithInvalidBirdData() {
        final ResponseEntity response = restTemplate.postForEntity("/birds", invalidBirdData(), ResponseEntity.class);
        assertThat(response.getStatusCode(), Matchers.equalTo(HttpStatus.BAD_REQUEST));

    }

    @Test
    public void saveBirdOnPostWithCorrectBirdData() {
        final Bird input = validBirdData().build();
        final Bird bird = restTemplate.postForObject("/birds", input, Bird.class);
        assertThat(bird, Matchers.notNullValue());
        assertThat(bird.getId(), Matchers.notNullValue());
        assertThat(fetchFromDb(bird.getId()), Matchers.equalTo(input));
    }

    private Bird fetchFromDb(ObjectId id) {
        return birdsRegistryDao.findOne(id);
    }

    @Test
    public void getBirdById() {
        final Bird validBird = validBirdData().visibility(true).build();
        birdsRegistryDao.save(validBird);
        final Bird response = restTemplate.getForObject("/birds/{id}", Bird.class, validBird.getIdAsHex());
        assertThat(response, Matchers.equalTo(validBird));
    }

    @Test
    public void notFoundOnInvalidId() {
        final ResponseEntity response = restTemplate.getForEntity("/birds/{id}", ResponseEntity.class, "randomId");
        assertThat(response.getStatusCode(), Matchers.equalTo(HttpStatus.NOT_FOUND));
    }

    @Test
    public void getAllVisibleBirds() {
        final Bird visibleBirdOne = validBirdData().visibility(true).build();
        final Bird visibleBirdTwo = validBirdData().visibility(true).build();
        final Bird visibleBirdThree = validBirdData().visibility(true).build();

        saveBirds(visibleBirdOne, visibleBirdTwo, visibleBirdThree);

        saveBirds(validBirdData().visibility(false).build());
        saveBirds(validBirdData().visibility(false).build());

        final List birds = restTemplate.getForObject("/birds", List.class);

        assertThat(birds.size(), Matchers.equalTo(3));
        assertTrue(birds.contains(visibleBirdOne.getIdAsHex()));
        assertTrue(birds.contains(visibleBirdTwo.getIdAsHex()));
        assertTrue(birds.contains(visibleBirdThree.getIdAsHex()));
    }

    @Test
    public void shouldDeleteBirdByValidId() {
        final Bird validBird = validBirdData().visibility(true).build();
        saveBirds(validBird);
        restTemplate.delete("/birds/{id}", validBird.getIdAsHex());
        assertThat(fetchFromDb(validBird.getId()), Matchers.nullValue());
    }

    @Test
    public void notFoundOnDeleteForInvalidId() throws IOException {
        ClientHttpResponse clientHttpResponse = restTemplate.execute("/birds/{id}", HttpMethod.DELETE, null, response -> response, "randomId");
        assertThat(clientHttpResponse.getStatusCode(), Matchers.equalTo(HttpStatus.NOT_FOUND));
    }

    private void saveBirds(Bird... birds) {
        for (Bird bird : birds) {
            birdsRegistryDao.save(bird);
        }

    }


    private Bird invalidBirdData() {
        return Bird.builder()
            .name(null)
            .family("birdFamily")
            .continents(newHashSet("north america", "asia"))
            .visibility(false)
            .addedOn(DateUtil.currentDate())
            .build();
    }

    private Bird.BirdBuilder validBirdData() {
        return Bird.builder()
            .name("birdName")
            .family("birdFamily")
            .continents(newHashSet("north america", "asia"))
            .visibility(true)
            .addedOn(DateUtil.currentDate());

    }


}