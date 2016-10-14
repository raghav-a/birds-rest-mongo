package com.birds.controller;


import com.birds.dao.BirdsDao;
import com.birds.dao.BirdsRepository;
import com.birds.dao.HashMapDao;
import com.birds.dao.MongoDbDao;
import com.birds.model.Bird;
import com.birds.model.BirdData;
import com.birds.payloads.BirdCreationResponse;
import com.birds.payloads.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;


@RestController
public class BirdsController {

    //private BirdsDao birdsDao = new HashMapDao();

    public BirdsController(BirdsDao birdsDao) {
        this.birdsDao = birdsDao;
    }

    @Autowired
    @Qualifier("birds_dao_mongodb")
    private BirdsDao birdsDao;


    @RequestMapping(value = "/birds/{id}", method = GET)
    public Bird get(@PathVariable(value = "id") BigInteger id) {
        return birdsDao.get(id);

    }

    @RequestMapping(value = "/birds", method = GET)
    public
    @ResponseBody
    Collection<Bird> getAll() {
        return birdsDao.values();

    }

    @RequestMapping(value = "/birds", method = POST)
    public
    @ResponseBody
    GenericResponse addBird(@RequestBody BirdData birdData) {
        final Bird bird = new Bird(birdData);
        birdsDao.put(bird.getId(), bird);
        return new BirdCreationResponse("ADDED_SUCCESSFULLY", bird.getId());

    }

    @RequestMapping(value = "/birds/{id}", method = DELETE)
    public
    @ResponseBody
    GenericResponse deleteBird(@PathVariable(value = "id") BigInteger id) {
        birdsDao.remove(id);
        String message = "DELETED_SUCCESSFULLY";
        return new GenericResponse(message);
    }


}
