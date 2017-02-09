package com.birds.controller;


import com.birds.controller.data.BirdData;
import com.birds.dao.BirdsDao;
import com.birds.exceptions.ApplicationException;
import com.birds.model.Bird;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.stream.Collectors;

import static com.birds.helpers.JsonBuilder.JsonBuilderFactory.jsonBuilderFactory;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;


@RestController
public class BirdsRegistryController {


    private static final ResponseEntity<String> NOT_FOUND = ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    @Autowired
    @Qualifier("birds_dao_mongodb")
    private BirdsDao birdsDao;

    public BirdsRegistryController(BirdsDao birdsDao) {
        this.birdsDao = birdsDao;
    }

    @RequestMapping(value = "/birds/{id}", method = GET)
    public ResponseEntity<String> get(@PathVariable(value = "id") String id) {
        final Bird bird = birdsDao.get(getValidObjectId(id));
        return bird == null ?
            NOT_FOUND :
            new ResponseEntity<>(bird.toJson(jsonBuilderFactory()), HttpStatus.OK);

    }


    @RequestMapping(value = "/birds", method = GET)
    public
    @ResponseBody
    Collection<String> getAll() {
        return birdsDao.getAll()
            .stream()
            .filter(Bird::isVisibile)
            .map(Bird::idAsHex)
            .collect(Collectors.toList());
    }

    @RequestMapping(value = "/birds", method = POST)
    public
    @ResponseBody
    ResponseEntity<String> addBird(@RequestBody BirdData birdData) {
        final Bird bird = new Bird(birdData);
        birdsDao.save(bird);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(bird.toJson(jsonBuilderFactory()));

    }

    @RequestMapping(value = "/birds/{id}", method = DELETE)
    public
    @ResponseBody
    ResponseEntity<String> deleteBird(@PathVariable(value = "id") String id) {
        final ObjectId objectId = getValidObjectId(id);
        return birdsDao.get(objectId) == null ? NOT_FOUND : delete(objectId);
    }

    public ObjectId getValidObjectId(String string) {
        try {
            return new ObjectId(string);
        } catch (RuntimeException e) {
            throw new ApplicationException(HttpStatus.NOT_FOUND);
        }

    }

    private ResponseEntity<String> delete(ObjectId id) {
        birdsDao.remove(id);
        return ResponseEntity.status(HttpStatus.OK)
            .body(null);
    }

}
