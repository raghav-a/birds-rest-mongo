package com.birds.dao;

import com.birds.model.Bird;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.math.BigInteger;
import java.util.List;


public interface BirdsRepository extends MongoRepository<Bird, BigInteger> {


}
