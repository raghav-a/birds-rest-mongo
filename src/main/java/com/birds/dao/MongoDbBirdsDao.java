package com.birds.dao;


import com.birds.model.Bird;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component("birds_dao_mongodb")
public class MongoDbBirdsDao implements BirdsDao {

    @Autowired
    BirdsRepository birdsRepository;


    @Override
    public Bird get(ObjectId id) {
        return birdsRepository.findOne(id);
    }

    @Override
    public Collection<Bird> getAll() {
        return birdsRepository.findAll();
    }

    @Override
    public void save(Bird bird) {
        birdsRepository.insert(bird);
    }

    @Override
    public void remove(ObjectId id) {
        birdsRepository.delete(id);
    }

}
