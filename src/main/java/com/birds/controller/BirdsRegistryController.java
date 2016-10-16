package com.birds.controller;


import com.birds.DateUtil;
import com.birds.dao.BirdsRegistryDao;
import com.birds.exceptions.ApplicationException;
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

    private static final ResponseEntity<String> NOT_FOUND = ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

    public BirdsRegistryController(BirdsRegistryDao birdsRegistryDao) {
        this.birdsRegistryDao = birdsRegistryDao;
    }

    @Autowired
    @Qualifier("birds_dao_mongodb")
    private BirdsRegistryDao birdsRegistryDao;


    @RequestMapping(value = "/birds/{id}", method = GET)
    public ResponseEntity<String> get(@PathVariable(value = "id") String id) {
        final Bird bird = birdsRegistryDao.get(getValidObjectId(id));
        return bird == null ?
            NOT_FOUND : new ResponseEntity<>(bird.toJson(), HttpStatus.OK);

    }


    @RequestMapping(value = "/birds", method = GET)
    public
    @ResponseBody
    Collection<String> getAll() {
        return birdsRegistryDao.getAll()
            .stream()
            .filter(Bird::getVisibility)
            .map(Bird::getIdAsHex)
            .collect(Collectors.toList());
    }

    @RequestMapping(value = "/birds", method = POST)
    public
    @ResponseBody
    ResponseEntity<String> addBird(@RequestBody BirdData birdData) {
        birdData.validate();
        final Bird bird = new Bird(birdData, DateUtil.currentDate());
        birdsRegistryDao.save(bird);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(bird.toJson());

    }

    @RequestMapping(value = "/birds/{id}", method = DELETE)
    public
    @ResponseBody
    ResponseEntity<String> deleteBird(@PathVariable(value = "id") String id) {
        final ObjectId objectId = getValidObjectId(id);
        return birdsRegistryDao.get(objectId) == null ? NOT_FOUND : delete(objectId);
    }

    private ResponseEntity<String> delete(ObjectId id) {
        birdsRegistryDao.remove(id);
        return ResponseEntity.status(HttpStatus.OK)
            .body(null);
    }

    public ObjectId getValidObjectId(String string) {
        try {
            return new ObjectId(string);
        } catch (RuntimeException e) {
            throw new ApplicationException(HttpStatus.NOT_FOUND);
        }

    }

}
