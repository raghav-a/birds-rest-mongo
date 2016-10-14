package com.birds.dao;


import com.birds.model.Bird;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.Collection;

@Component("birds_dao_mongodb")
public class MongoDbDao implements BirdsDao {

    @Autowired
    BirdsRepository birdsRepository;


    @Override
    public Bird get(BigInteger id) {
        return birdsRepository.findOne(id);
    }

    @Override
    public Collection<Bird> values() {
        return birdsRepository.findAll();
    }

    @Override
    public void put(BigInteger id, Bird bird) {
        birdsRepository.insert(bird);
    }

    @Override
    public void remove(BigInteger id) {
         birdsRepository.delete(id);
    }
}
