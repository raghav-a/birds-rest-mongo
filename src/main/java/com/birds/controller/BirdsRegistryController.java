package com.birds.controller;


import com.birds.DateUtil;
import com.birds.dao.BirdsRegistryDao;
import com.birds.model.Bird;
import com.birds.model.BirdData;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.stream.Collectors;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;


@RestController
public class BirdsRegistryController {

    public BirdsRegistryController(BirdsRegistryDao birdsRegistryDao) {
        this.birdsRegistryDao = birdsRegistryDao;
    }

    @Autowired
    @Qualifier("birds_dao_mongodb")
    private BirdsRegistryDao birdsRegistryDao;


    @RequestMapping(value = "/birds/{id}", method = GET)
    public ResponseEntity<String> get(@PathVariable(value = "id") String id) {
        ObjectId objectId = null;
        try {
            objectId = new ObjectId(id);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        final Bird bird = birdsRegistryDao.get(objectId);
        return bird == null ?
            new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<>(bird.toJson(), HttpStatus.OK);

    }

    @RequestMapping(value = "/birds", method = GET)
    public
    @ResponseBody
    Collection<String> getAll() {
        return birdsRegistryDao.getAll()
            .stream()
            .filter(Bird::getVisibility)
            .map(bird -> bird.getIdAsHex())
            .collect(Collectors.toList());
    }

    @RequestMapping(value = "/birds", method = POST)
    public
    @ResponseBody
    ResponseEntity<String> addBird(@RequestBody BirdData birdData) {
        try {
            birdData.validate();
        } catch (BirdData.InvalidBirdDataException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(null);
        }

        final Bird bird = new Bird(birdData, DateUtil.currentDate());
        birdsRegistryDao.save(bird);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(bird.toJson());

    }

    @RequestMapping(value = "/birds/{id}", method = DELETE)
    public
    @ResponseBody
    ResponseEntity<String> deleteBird(@PathVariable(value = "id") String id) {
        ObjectId objectId = null;
        try {
            objectId = new ObjectId(id);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return birdsRegistryDao.get(objectId) == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(null) : delete(objectId);
    }

    private ResponseEntity<String> delete(ObjectId id) {
        birdsRegistryDao.remove(id);
        return ResponseEntity.status(HttpStatus.OK)
            .body(null);
    }

}
